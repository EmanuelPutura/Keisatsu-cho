package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Service
class FileUploadService {
    private val uploadFileDirectory: String = System.getProperty("user.dir") + "\\uploaded\\"

    fun getFileUploadPath(file: MultipartFile): String = uploadFileDirectory + file.originalFilename

    fun uploadFile(file: MultipartFile) {
        val filepath = Paths.get(getFileUploadPath(file))
        val outputStream = Files.newOutputStream(filepath)
        outputStream.write(file.bytes);
    }
}
