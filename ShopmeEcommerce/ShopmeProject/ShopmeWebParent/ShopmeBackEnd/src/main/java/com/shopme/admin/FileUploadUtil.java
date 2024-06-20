package com.shopme.admin;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String uploadDir, String fileName, 
			MultipartFile multipartFile) throws IOException{
		Path uploadPath = Paths.get(uploadDir);//Tạo một đối tượng Path từ đường dẫn thư mục được truyền vào qua tham số uploadDir.
		if (!Files.exists(uploadPath)) {// Kiểm tra xem thư mục uploadPath đã tồn tại trên hệ thống tệp không.
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {//multipartFile.getInputStream() cho phép ta lấy InputStream từ MultipartFile, sau đó sử dụng Files.copy để sao chép dữ liệu từ inputStream vào đường dẫn filePath trên hệ thống tệp
			Path filePath = uploadPath.resolve(fileName);//Tạo đường dẫn đầy đủ của tệp đích trong thư mục uploadPath, với tên là fileName
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);//Sao chép dữ liệu từ inputStream vào filePath. Nếu tệp đích đã tồn tại, sử dụng StandardCopyOption.REPLACE_EXISTING để ghi đè lên tệp cũ.
		} catch (IOException ex) {
			throw new IOException("Could not save file: " + fileName, ex);
		}
	}
	
	public static void cleanDir(String dir) {
			// duong dan thu muc can lam sach) {//làm sạch một thư mục, tức là xóa tất cả các tệp không phải là thư mục con trong thư mục được chỉ định
		//là một phương thức tĩnh (static), có nghĩa là nó có thể được gọi mà không cần tạo một đối tượng của lớp chứa nó.
		Path dirPath = Paths.get(dir);
/*	chuyển đổi chuỗi ký tự dir thành một đối tượng Path
*/			
	try {//liệt kê các tệp trong thư mục và xử lý từng tệp:
			Files.list(dirPath).forEach(file -> {//tạo một stream của tất cả các tệp và thư mục con trong thư mục dirPath. và duyệt qua từng phần tử của nó
				if (!Files.isDirectory(file)) {//kiểm tra xem phần tử hiện tại có phải là thư mục không. Nếu không phải thư mục (tức là tệp)
					try {
						Files.delete(file);
					} catch (IOException ex) {
						System.out.println("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException ex) {//Nếu có lỗi xảy ra khi liệt kê các tệp trong thư mục, IOException sẽ bị bắt và thông báo lỗi sẽ được in ra: 
			System.out.println("Could not list directory: " + dirPath);
		}
	}
}
