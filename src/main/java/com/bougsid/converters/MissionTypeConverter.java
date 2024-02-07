package com.bougsid.converters;

import com.bougsid.mission.AddMissionView;
import com.bougsid.missiontype.MissionType;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by ayoub on 6/29/2016.
 */
@FacesConverter("missionTypeConverter")
public class MissionTypeConverter implements javax.faces.convert.Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                AddMissionView addMissionView = facesContext.getApplication().evaluateExpressionGet(facesContext,"#{addMissionView}", AddMissionView.class);
                MissionType missionType = addMissionView.getMissionTypes().stream().filter(b -> b.getId() == Long.valueOf(value)).findFirst().get();
                return missionType;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
//            System.out.println("class ="+o.getClass());
            return String.valueOf(o);
        } else {
            return null;
        }
    }
}
