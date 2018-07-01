package harry.FileServer.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import harry.FileServer.entity.ClusterFile;
import harry.FileServer.util.HadoopClusterFileUtil;
import harry.FileServer.util.MD5Util;

/**
 * 
 * @author harry
 *
 */
@Controller
public class FileController {
	@PostMapping("/upload")
	@ResponseBody
	public ClusterFile upload(@RequestParam("file") MultipartFile file) throws IOException{
		String fileSign = MD5Util.sign(file.getInputStream());
		System.out.println(fileSign);
		
		ClusterFile clusterFile = HadoopClusterFileUtil.validate(fileSign);
		if(clusterFile == null){
			clusterFile = HadoopClusterFileUtil.save(file.getInputStream(), file.getSize(), fileSign);
		}
		
		return clusterFile;
	}
}
