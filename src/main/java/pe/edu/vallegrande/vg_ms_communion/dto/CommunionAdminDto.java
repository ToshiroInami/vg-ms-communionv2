package pe.edu.vallegrande.vg_ms_communion.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommunionAdminDto {
    private String names;
    private String surnames;
    private String placeCommunion;
    private boolean enrolledCatechesis;
    private String priest;
    private Date communionDate;
    private String comment;
    private char state;
}
