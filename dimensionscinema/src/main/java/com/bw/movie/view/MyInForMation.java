package com.bw.movie.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApp;
import com.bw.movie.R;
import com.bw.movie.adapter.MmActivity;
import com.bw.movie.bean.AllUser;
import com.bw.movie.bean.QureyUser;
import com.bw.movie.bean.Result;
import com.bw.movie.core.DataCall;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.dao.AllUserDao;
import com.bw.movie.presenter.ModifyUserPresenter;
import com.bw.movie.presenter.QureyUserPresenter;
import com.bw.movie.presenter.UpHeadPresenter;
import com.bw.movie.utils.jilei.WDActivity;
import com.bw.movie.utils.util.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInForMation extends WDActivity implements View.OnClickListener {
    @BindView(R.id.msim_my)
    SimpleDraweeView msim_my;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_birthdate)
    TextView tv_birthdate;
    @BindView(R.id.tv_telephone)
    TextView tv_telphone;
    @BindView(R.id.tv_email)
    TextView tv_email;
    private QureyUserPresenter qureyUserPresenter;
    private int userId;
    private String sessionId;
    private String nickName;
    private int sex;
    private QureyUser result;
    private List<Object> objects = new ArrayList<>();
    private SimpleDraweeView simpleDraweeView;
    private ModifyUserPresenter modifyUserPresenter;
    private int index = 0;// 记录单选对话框的下标
    private String newsex;
    private List<AllUser> allUsers;
    private AllUser allUser;
    private AllUserDao allUserDao;

    @Override
    protected void initView() {
        //绑定
        ButterKnife.bind(this);
        //查询数据库
        allUserDao = MyApp.daoSession.getAllUserDao();
        allUsers = allUserDao.loadAll();
        if (allUsers.size() > 0) {
            allUser = allUsers.get(0);
            userId = allUser.getUserId();
            sessionId = allUser.getSessionId();
        }
        //设置数据
        qureyUserPresenter = new QureyUserPresenter(new QureyUserCall());
        qureyUserPresenter.request(userId, sessionId);
        //修改用户信息
        myupdate();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_username:
                final EditText editText = new EditText(this);
                editText.setText(result.getNickName());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("修改用户名称");
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = editText.getText().toString().trim();
                        allUsers.get(0).setNickName(s);
                        allUserDao.update(allUsers.get(0));
                        tv_username.setText(s);
                        modifyUserPresenter = new ModifyUserPresenter(new UpHeadC());
                        modifyUserPresenter.request(userId, sessionId, s, result.getSex(), result.getEmail());
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
            case R.id.tv_sex:
                final int sex1 = result.getSex();
                int sexid = 0;
                if (sex1 == 1) {
                    sexid = 0;
                } else {
                    sexid = 1;
                }
                final String[] sex = {"男", "女"};
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setSingleChoiceItems(sex, sexid, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                });
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newsex = sex[index];
                        tv_sex.setText(newsex);
                        if (newsex.equals("男")) {
                            modifyUserPresenter = new ModifyUserPresenter(new UpHeadC());
                            modifyUserPresenter.request(userId, sessionId, nickName, 1, result.getEmail());
                        } else {
                            modifyUserPresenter = new ModifyUserPresenter(new UpHeadC());
                            modifyUserPresenter.request(userId, sessionId, nickName, 2, result.getEmail());

                        }
                    }
                });
                // 显示对话框
                builder2.show();
                break;
            case R.id.tv_email:
                final EditText editemail = new EditText(this);
                editemail.setText(result.getEmail());
                AlertDialog builder3 = new AlertDialog.Builder(this)
                        .setTitle("修改邮箱")
                        .setView(editemail)//设置输入框
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String trim = editemail.getText().toString().trim();
                                /*boolean email = isEmail(trim + "");*/
                                /*if (email) {*/
                                tv_email.setText(trim);
                                modifyUserPresenter = new ModifyUserPresenter(new UpHeadC());
                                modifyUserPresenter.request(userId, sessionId, nickName, result.getSex(), trim);
                                /*} else {
                                    UIUtils.showToastSafe("请输入正确的邮箱");
                                    return;
                                }*/
                            }
                        }).setNegativeButton("取消", null).create();
                builder3.show();

                break;
            case R.id.my_head:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1, 0);
                break;
            case R.id.in_for_mm:
                //跳转
                Intent intent = new Intent(MyInForMation.this, MmActivity.class);
                startActivity(intent);
                break;
        }
    }

    //实现修改用户接口
    class ModifyCall implements DataCall<Result>{
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(MyInForMation.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    private void myupdate() {
        findViewById(R.id.my_head).setOnClickListener(this);
        findViewById(R.id.tv_username).setOnClickListener(this);
        findViewById(R.id.tv_sex).setOnClickListener(this);
        findViewById(R.id.tv_email).setOnClickListener(this);
        findViewById(R.id.in_for_mm).setOnClickListener(this);
        simpleDraweeView = findViewById(R.id.msim_my);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_in_for_mation;
    }


    //实现查询用户信息
    class QureyUserCall implements DataCall<Result<QureyUser>> {
        @Override
        public void success(Result<QureyUser> data) {
            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                String headPic = result.getHeadPic();
                msim_my.setImageURI(headPic);
                nickName = result.getNickName();
                tv_username.setText(nickName);
                sex = result.getSex();
                String sex3 = sex == 1 ? "男" : "女";
                tv_sex.setText(sex3);
                long birthday = result.getBirthday();
                Date date = new Date(birthday);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String s = format.format(date);
                tv_birthdate.setText(s);
                String phone = result.getPhone();
                tv_telphone.setText(phone);
                String email = result.getEmail();
                tv_email.setText(email);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //点击返回
    @OnClick(R.id.mreturn)
    public void mreturn() {

        finish();
    }

    //上传用户头像
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            String filePath = getFilePath(null, requestCode, data);
            objects.add(filePath);
            Uri data1 = data.getData();
            simpleDraweeView.setImageURI(data1);
            allUsers.get(0).setHeadPic(data1.toString());
            allUserDao.update(allUsers.get(0));
            UpHeadPresenter upHeadPresenter = new UpHeadPresenter(new UpHeadC());
            upHeadPresenter.request(userId, sessionId, objects);
            objects.clear();
            allUser.setHeadPic(data1.toString());
        }
    }
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == 1) {
            return fileName;
        } else if (requestCode == 0) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close();
            return img_path;
        }
        return null;
    }
    class UpHeadC implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(MyInForMation.this, data.getMessage()+"", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}