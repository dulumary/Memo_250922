package com.marondal.memo.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public static final String FILE_UPLOAD_PATH = "E:\\dulumaryT\\web\\20250528\\springProject\\upload\\memo";

    public static String saveFile(long userId, MultipartFile file) {

        if(file == null) {
            return null;
        }

        // 디렉토리 (폴더) 를 만들어서 파일 저장
        // 디렉토리 이름 : 사용자 정보 + 시간 정보 (ex) 2_3490943099
        // UNIX TIME : 1970년 1월 1일 0시 0분 0초 이후로 흐른 시간  (millisecond)

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();
        // 디렉토리 만들기
        // 전체 디렉토리 경로
        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        File directory = new File(directoryPath);

        if(!directory.mkdir()) {
            // 디렉토리 생성 실패
            return null;
        }

        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(filePath);
            Files.write(path, bytes);

        } catch (IOException e) {
            return null;
        }

//      E:\\dulumaryT\\web\\20250528\\springProject\\upload\\memo/3_194509128/test.png"
//      /images/3_194509128/test.png

        return "/images" + directoryName + "/" + file.getOriginalFilename();
    }

    public static boolean removeFile(String imagePath) {  // /images/3_194509128/test.png

        //      E:\\dulumaryT\\web\\20250528\\springProject\\upload\\memo/3_194509128/test.png"

        if(imagePath == null) {
            return false;
        }

        String fullFilePath = FILE_UPLOAD_PATH + imagePath.replace("/images", "");

        Path path = Paths.get(fullFilePath);
        Path directoryPath = path.getParent();

        try {
            Files.delete(path);
            Files.delete(directoryPath);

        } catch (IOException e) {
            return false;
        }

        return true;

    }



}
