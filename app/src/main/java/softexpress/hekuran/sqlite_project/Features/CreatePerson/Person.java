package softexpress.hekuran.sqlite_project.Features.CreatePerson;

import java.util.Date;

public class Person {
    private int id;
    private String emri;
    private String mbiemri;
    private Date datelindja;
    private String telefon;
    private String gjinia;
    private int ipunesuar;
    private String gjendjamartesore;
    private String vendlindja;


    public Person(int id, String emri, String mbiemri, Date datelindja, String telefon, String gjinia, int ipunesuar, String gjendjamartesore, String vendlindja) {
        this.id = id;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.datelindja = datelindja;
        this.telefon = telefon;
        this.gjinia = gjinia;
        this.ipunesuar = ipunesuar;
        this.gjendjamartesore = gjendjamartesore;
        this.vendlindja = vendlindja;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public Date getDatelindja() {
        return datelindja;
    }

    public void setDatelindja(Date datelindja) {
        this.datelindja = datelindja;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getGjinia() {
        return gjinia;
    }

    public void setGjinia(String gjinia) {
        this.gjinia = gjinia;
    }

    public int isIpunesuar() {
        return ipunesuar;
    }

    public void setIpunesuar(int ipunesuar) {
        this.ipunesuar = ipunesuar;
    }

    public String getGjendjamartesore() {
        return gjendjamartesore;
    }

    public void setGjendjamartesore(String gjendjamartesore) {
        this.gjendjamartesore = gjendjamartesore;
    }

    public String getVendlindja() {
        return vendlindja;
    }

    public void setVendlindja(String vendlindja) {
        this.vendlindja = vendlindja;
    }



}
