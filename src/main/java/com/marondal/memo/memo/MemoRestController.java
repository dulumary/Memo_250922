package com.marondal.memo.memo;

import com.marondal.memo.memo.service.MemoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/memo")
@RestController
public class MemoRestController {

    private final MemoService memoService;

    public MemoRestController(MemoService memoService) {
        this.memoService = memoService;
    }


    @PostMapping("/write-process")
    public Map<String, String> writeMemo(
            @RequestParam String title
            , @RequestParam String contents
            , @RequestParam(required=false) MultipartFile imageFile
            , HttpSession session) {

        long userId = (Long)session.getAttribute("userId");

        Map<String, String> resultMap = new HashMap<>();
        if(memoService.createMemo(userId, title, contents, imageFile)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }


    @PutMapping("/modify")
    public Map<String, String> modifyMemo(
            @RequestParam long id
            , @RequestParam String title
            , @RequestParam String contents) {

        Map<String, String> resultMap = new HashMap<>();

        if(memoService.updateMemo(id, title, contents)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @DeleteMapping("/remove")
    public Map<String, String> removeMemo(@RequestParam long id) {

        Map<String, String> resultMap = new HashMap<>();

        if(memoService.deleteMemo(id)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;

    }

}
