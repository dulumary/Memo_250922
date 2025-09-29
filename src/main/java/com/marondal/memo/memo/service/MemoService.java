package com.marondal.memo.memo.service;

import com.marondal.memo.memo.domain.Memo;
import com.marondal.memo.memo.repository.MemoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public boolean createMemo(long userId, String title, String contents) {

        Memo memo = Memo.builder()
                .userId(userId)
                .title(title)
                .contents(contents)
                .build();

        try {
            memoRepository.save(memo);
        } catch(DataAccessException e) {
            return false;
        }

        return true;
    }

    public List<Memo> getMemoList(long userId) {
        return memoRepository.findByUserId(userId, Sort.by("id").descending());
    }

}
