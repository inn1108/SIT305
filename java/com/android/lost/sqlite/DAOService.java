package com.android.lost.sqlite;

import com.android.lost.entity.LostAndFound;
import com.android.lost.utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import org.litepal.LitePal;

import java.util.List;

public class DAOService {
    private static DAOService DAOService;


    private DAOService() {
    }

    public static DAOService getInstance() {
        if (null == DAOService) {
            DAOService = new DAOService();
        }
        return DAOService;
    }

    private LatLng latLng;
    private String address;


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 插入失物信息
     */
    public void insertHealthInfo(LostAndFound lostAndFound){
        lostAndFound.save();
    }

    /**
     * 根据开始和结束时间 查询区间内的失物信息
     */
    public List<LostAndFound> searchHealthInfoByStartAndEnd(){
        return LitePal.findAll(LostAndFound.class);
    }

    /**
     * 查询指定id的失物信息
     */
    public LostAndFound searchByHealthId(int id){
        return LitePal.find(LostAndFound.class,id);
    }

    /**
     * 删除失物信息
     */
    public void deleteTrafficInfoById(int id){
        LitePal.delete(LostAndFound.class, id);
    }
}