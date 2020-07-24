package model;

import java.io.Serializable;

public class TaiKhoanCH implements Serializable {
    private String email;
    private String password;
    private String macuahang;

    public TaiKhoanCH(String email, String password, String macuahang) {
        this.email = email;
        this.password = password;
        this.macuahang = macuahang;
    }

    public TaiKhoanCH() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacuahang() {
        return macuahang;
    }

    public void setMacuahang(String macuahang) {
        this.macuahang = macuahang;
    }
}
