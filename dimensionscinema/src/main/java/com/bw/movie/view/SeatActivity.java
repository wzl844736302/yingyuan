package com.bw.movie.view;


import com.bw.movie.R;
import com.bw.movie.utils.jilei.WDActivity;

public class SeatActivity extends WDActivity {
    public SeatTable seatTableView;
    @Override
    protected void initView() {
        seatTableView = findViewById(R.id.seat_seat);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seat;
    }
}
