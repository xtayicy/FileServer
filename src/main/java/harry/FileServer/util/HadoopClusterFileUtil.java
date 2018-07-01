package harry.FileServer.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import harry.FileServer.constant.ClusterFileParameter;
import harry.FileServer.entity.ClusterFile;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author harry
 *
 */
public class HadoopClusterFileUtil {
	private static Jedis jedis;
	private static FileSystem fileSystem;

	static {
		try {
			jedis = new Jedis("192.168.0.128", 6379);
			Configuration configuration = new Configuration();
			configuration.addResource("core-site.xml");
			configuration.addResource("hdfs-site.xml");
			fileSystem = FileSystem.get(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ClusterFile validate(String fileSign) {
		Boolean exists = jedis.exists(fileSign);
		if (!exists) {
			return null;
		}

		long fileSize = Long.parseLong(jedis.hmget(fileSign, ClusterFileParameter.FILE_SIZE).get(0));
		String filePath = jedis.hmget(fileSign, ClusterFileParameter.FILE_PATH).get(0);

		ClusterFile fileNode = new ClusterFile();
		fileNode.setFilePath(filePath);
		fileNode.setFileSign(fileSign);
		fileNode.setSize(fileSize);

		return fileNode;
	}

	public static ClusterFile save(InputStream in, Long fileSize, String fileSign) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String basePath = sdf.format(new Date());
		Path path = new Path("/" + basePath);
		String fileName = UUID.randomUUID().toString().replace("-", "");
		Map<String, String> map = new HashMap<String, String>();
		map.put(ClusterFileParameter.FILE_PATH, new Path(path + "/" + fileName).toString());
		map.put(ClusterFileParameter.IS_FINISHED, Boolean.TRUE.toString());
		map.put(ClusterFileParameter.FILE_SIZE, fileSize.toString());
		jedis.hmset(fileSign, map);
		try {
			if (!fileSystem.exists(path)) {
				fileSystem.mkdirs(path);
			}

			FSDataOutputStream out = fileSystem.create(new Path(path + "/" + fileName));
			IOUtils.copyBytes(in, out, 2048, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ClusterFile clusterFile = new ClusterFile();
		clusterFile.setFilePath(map.get(ClusterFileParameter.FILE_PATH));
		clusterFile.setFileSign(fileSign);
		clusterFile.setSize(fileSize);

		return clusterFile;
	}
}
