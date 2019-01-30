package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.Irequest;
import com.bw.movie.utils.NetWorkUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpHeadPresenter extends BasePresenter {

    public UpHeadPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        Irequest irequest = NetWorkUtils.getMinstance().create(Irequest.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        List<Object> list = (List<Object>) args[2];
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/octet-stream"), file));
            }
        }
        return irequest.uploadHeadPic((int) args[0], (String) args[1],builder.build());


    }
}
