package com.example.lost.sqlite;

import com.example.lost.entity.LostFind;

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


    public void insertHealthInfo(LostFind lostFind){
        lostFind.save();
    }


    public List<LostFind> searchHealthInfoByStartAndEnd(){
        return LitePal.findAll(LostFind.class);
    }


    public LostFind searchByHealthId(int id){
        return LitePal.find(LostFind.class,id);
    }


    public void deleteTrafficInfoById(int id){
        LitePal.delete(LostFind.class, id);
    }
}