package harry.FileServer.entity;

/**
 * 
 * @author harry
 *
 */
public class ClusterFile {
	private long size;
	private String fileSign;
	private String filePath;
	
	public ClusterFile() {
		super();
	}
	public ClusterFile(long size, String fileSign,String filePath) {
		super();
		this.size = size;
		this.fileSign = fileSign;
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getFileSign() {
		return fileSign;
	}
	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	@Override
	public String toString() {
		return "ClusterFile [size=" + size + ", fileSign=" + fileSign + ", filePath=" + filePath + "]";
	}
}
