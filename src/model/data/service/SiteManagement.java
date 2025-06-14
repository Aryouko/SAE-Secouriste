package model.data.service;

import model.dao.SiteDAO;
import model.data.persistence.Site;

import java.util.List;

public class SiteManagement {
    private final SiteDAO siteDAO = new SiteDAO();

    public List<Site> getSites(){
        return this.siteDAO.findAll();
    }

    public Site getSiteByName(String name){
        return this.siteDAO.findByName(name);
    }

    public Site getSiteById(int id){
        return this.siteDAO.findById(id);
    }
}
