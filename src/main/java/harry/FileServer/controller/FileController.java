package harry.FileServer.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
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
