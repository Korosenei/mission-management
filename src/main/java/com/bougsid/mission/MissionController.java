package com.bougsid.mission;

import com.bougsid.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ayoub on 6/26/2016.
 */

@Controller
public class MissionController {
    @Autowired
    private IMissionService missionService;
    @Autowired
    private MSG msg;


    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String errorPage() {
        return "404";
    }

    @RequestMapping(value = "/decompte", method = RequestMethod.GET)
    public String decomptePage() {
        return "decompte";
    }

    @RequestMapping(value = "/addmission", method = RequestMethod.GET)
    public String addMissionPage() {
        return "addmission";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmMission(@RequestParam(value = "mission") String uuid
    ,@RequestParam(value = "s") String secret) {
        System.out.println("Mission = " + uuid);
        if (this.missionService.validateMissionByUuid(uuid,secret)) {
            return "login?state=success";
        }
        return "login?state=error";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    public String rejectMission(@RequestParam(value = "mission") String uuid
            ,@RequestParam(value = "s") String secret) {
        System.out.println("Mission = " + uuid);
        if (this.missionService.rejectMissionByUuid(uuid,secret)) {
            return "login?state=success";
        }
        return "login?state=error";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPag(){
        return loginPage();
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
    /* The user is logged in :) */
            return "redirect:/addmission";
        }
        return "login";
    }

    @RequestMapping(value = "/missions", method = RequestMethod.GET)
    public String missionsPage() {
        return "missions";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String statisticsPage() {
        return "statistics";
    }

    @RequestMapping(value = "/entreprises", method = RequestMethod.GET)
    public String entreprisesPage() {
        return "entreprises";
    }

    @RequestMapping(value = "/taux", method = RequestMethod.GET)
    public String tauxPage() {
        return "taux";
    }

    @RequestMapping(value = "/transport", method = RequestMethod.GET)
    public String transportPage() {
        return "transport";
    }

    @RequestMapping(value = "/villes", method = RequestMethod.GET)
    public String villesPage() {
        return "villes";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/download/{uuid}/{name}/{type}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("uuid") String uuid,
            @PathVariable("name") String name,
            @PathVariable("type") String type,
            HttpServletResponse response) {
        try {
            System.out.println("Downloading ...");
            if (type.equals("pdf"))
                response.setContentType("application/pdf");
            else
                response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "inline; filename=" + name + "." + type
            );

            // get your file as InputStream
            File downloadDir = new File(msg.getMessage("application.mission.downloaddir"));
            InputStream is = new FileInputStream(downloadDir.getPath() + "/" + uuid + "." + type);

            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            System.out.println("Finish Downloading ...");

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
