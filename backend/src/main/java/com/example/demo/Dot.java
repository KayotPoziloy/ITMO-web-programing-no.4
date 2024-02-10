package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "dots")
public class Dot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String x;
    private String y;
    private float r;
    private boolean status;
    private String owner;

    public Dot() {
    }

    public Dot(String x, String y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        checkStatus();
    }

    private void checkStatus() {
        status = inArea(x,y, r);
    }

    private boolean inArea(String x, String y, float r) {
        return circle(Double.parseDouble(x), Double.parseDouble(y), r)
                || square(Double.parseDouble(x), Double.parseDouble(y), r)
                || triangle(Double.parseDouble(x), Double.parseDouble(y), r);
    }

    private boolean circle(Double x, Double y, float r) {
        if (r >= 0) {
            return (x <= 0 && y >= 0 && x*x + y*y <= (r/2)*(r/2));
        } else {
            return (x >= 0 && y <= 0 && x*x + y*y <= (r/2)*(r/2));
        }
    }

    private boolean triangle(Double x, Double y, float r) {
        if (r >= 0) {
            return (x >= 0 && y >= 0 && y <= (-2*x + r));
        } else {
            return (x <= 0 && y <= 0 && y >= (-2*x + r));
        }
    }

    private boolean square(Double x, Double y, float r) {
        if (r >= 0) {
            return (x >= 0 && x <= r/2 && y <= 0 && y >= -r);
        } else {
            return (x <= 0 && x >= r/2 && y >= 0 && y <= -r);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
