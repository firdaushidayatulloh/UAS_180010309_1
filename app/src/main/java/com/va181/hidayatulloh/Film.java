package com.va181.hidayatulloh;

import java.util.Date;

public class Film   {

    private int idFilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String aktor;
    private String genre;
    private String sinopsis;
    private String link;

    public Film(int idFilm, String judul, Date tanggal, String gambar, String aktor, String genre, String sinopsis, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.aktor = aktor;
        this.genre = genre;
        this.sinopsis = sinopsis;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm= idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getAktor() {
        return aktor;
    }

    public void setAktor(String aktor) {
        this.aktor = aktor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}