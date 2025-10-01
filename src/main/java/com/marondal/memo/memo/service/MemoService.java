package com.marondal.memo.memo.service;

import com.marondal.memo.common.FileManager;
import com.marondal.memo.memo.domain.Memo;
import com.marondal.memo.memo.repository.MemoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public boolean createMemo(
            long userId
            , String title
            , String contents
            , MultipartFile file) {

        String imagePath = FileManager.saveFile(userId, file);

        Memo memo = Memo.builder()
                .userId(userId)
                .title(title)
                .contents(contents)
                .imagePath(imagePath)
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

    public Memo getMemo(long id) {
        Optional<Memo> optionalMemo = memoRepository.findById(id);

        return optionalMemo.get();

    }

}
