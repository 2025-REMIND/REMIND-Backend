package com.remind.memorylog.domain.diary.exception;

import com.remind.memorylog.global.exception.BaseException;

public class ImageUploadFailedException extends BaseException {
    public ImageUploadFailedException() {
        super(DiaryErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
