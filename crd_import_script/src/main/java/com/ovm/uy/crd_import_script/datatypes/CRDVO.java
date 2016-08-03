package com.ovm.uy.crd_import_script.datatypes;

import java.util.Date;
import java.util.GregorianCalendar;

import org.postgis.PGgeometry;

public class CRDVO {
	private String planificacion;
	private int fid;
	private int direccion;
	private int disco;
	private int iminolta;
	private int imagen;
	private String dia;
	private String mes;
	private int fecha;
	private Date hora_pc;
	private double longitud;
	private double latitud;
	private double altitud;
	private double heading;
	private Date hora_gps;
	private int gps_status;
	private double gps_dilution;
	private int satelites;
	private String linea;
	private String error;
	private GregorianCalendar fechaArchivo;

	public String getPlanificacion() {
		return planificacion;
	}

	public void setPlanificacion(String planificacion) {
		this.planificacion = planificacion;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public int getDisco() {
		return disco;
	}

	public void setDisco(int disco) {
		this.disco = disco;
	}

	public int getIminolta() {
		return iminolta;
	}

	public void setIminolta(int iminolta) {
		this.iminolta = iminolta;
	}

	public int getImagen() {
		return imagen;
	}

	public void setImagen(int imagen) {
		this.imagen = imagen;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getFecha() {
		return fecha;
	}

	public void setFecha(int fecha) {
		this.fecha = fecha;
	}

	public Date getHora_pc() {
		return hora_pc;
	}

	public void setHora_pc(Date hora_pc) {
		this.hora_pc = hora_pc;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getAltitud() {
		return altitud;
	}

	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public Date getHora_gps() {
		return hora_gps;
	}

	public void setHora_gps(Date hora_gps) {
		this.hora_gps = hora_gps;
	}

	public int getGps_status() {
		return gps_status;
	}

	public void setGps_status(int gps_status) {
		this.gps_status = gps_status;
	}

	public double getGps_dilution() {
		return gps_dilution;
	}

	public void setGps_dilution(double gps_dilution) {
		this.gps_dilution = gps_dilution;
	}

	public int getSatelites() {
		return satelites;
	}

	public void setSatelites(int satelites) {
		this.satelites = satelites;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public GregorianCalendar getFechaArchivo() {
		return fechaArchivo;
	}

	public void setFechaArchivo(GregorianCalendar fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}

	public PGgeometry getGeometry() {
		org.postgis.Point pointToAdd = new org.postgis.Point();
		pointToAdd.setX(getLongitud());
		pointToAdd.setY(getLatitud());
		pointToAdd.setSrid(4326);
		return new PGgeometry(pointToAdd);
	}

}
