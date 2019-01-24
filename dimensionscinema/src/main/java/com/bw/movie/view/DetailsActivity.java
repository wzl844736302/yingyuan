package com.bw.movie.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.MovieDetail;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.presenter.DetailMoviePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetailsActivity extends AppCompatActivity implements CustomAdapt {

    private SimpleDraweeView movieimage;
    private TextView moviename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("id", 0);

        DetailMoviePresenter presenter = new DetailMoviePresenter(new DetailData());
        presenter.request(1770, "15482453997081770",id);

        initView();
    }

    private void initView() {
        movieimage = findViewById(R.id.details_image);
        moviename = findViewById(R.id.details_name);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class DetailData implements DataCall<Result<MovieDetail>> {
        @Override
        public void success(Result<MovieDetail> data) {
            Toast.makeText(DetailsActivity.this, data.getMessage()+"", Toast.LENGTH_SHORT).show();
            MovieDetail result = data.getResult();
            movieimage.setImageURI(Uri.parse(result.getImageUrl()));
            moviename.setText(result.getName());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
