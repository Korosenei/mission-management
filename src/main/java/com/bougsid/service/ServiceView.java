package com.bougsid.service;

import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class ServiceView implements Serializable {
    private Dept dept = new Dept();
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IServiceService serviceService;
    private Dept selectedDept;
    private List<Employe> employes;
    private List<Dept> depts;
    private int page;
    private int maxPages;

    @PostConstruct
    public void init() {
//        this.serviceService = OrderMissionApplication.getContext().getBean(IServiceService.class);
//        this.employeService = OrderMissionApplication.getContext().getBean(IEmployeService.class);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        //Get Services List
        this.page = 0;
        Page<Dept> servicePage = this.serviceService.findAll(this.page);
        this.depts = servicePage.getContent();
        this.maxPages = servicePage.getTotalPages();
        this.employes = this.employeService.findAll();

    }

    public void newService() {
        this.selectedDept = new Dept();
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.depts = this.serviceService.findAll(page - 1).getContent();
    }

    public void saveService() {
        this.serviceService.save(selectedDept);
    }

    public void deleteService(){
        this.serviceService.delete(selectedDept);
    }
    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Dept getSelectedDept() {
        return selectedDept;
    }

    public void setSelectedDept(Dept selectedDept) {
        this.selectedDept = selectedDept;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

