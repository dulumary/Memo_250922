package com.marondal.memo.memo;

import com.marondal.memo.memo.domain.Memo;
import com.marondal.memo.memo.service.MemoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/memo")
@Controller
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/list")
    public String list(
            Model model
            , HttpSession session) {

        long userId = (Long)session.getAttribute("userId");

        List<Memo> memoList = memoService.getMemoList(userId );

        model.addAttribute("memoList", memoList);

        return "memo/list";
    }

    @GetMapping("/write")
    public String memoForm() {
        return "memo/write";
    }
}
