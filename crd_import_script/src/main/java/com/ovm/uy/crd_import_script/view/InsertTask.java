package com.ovm.uy.crd_import_script.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.ovm.uy.crd_import_script.datatypes.CRDVO;
import com.ovm.uy.crd_import_script.view.config.Configuration;

import javafx.concurrent.Task;

public class InsertTask extends Task<Void> {
	private String folderName;
	private static int nextId = -1;
	// private static java.sql.Connection conn;

	public InsertTask(String sFolder) {
		folderName = sFolder;
	}

	@Override
	protected Void call() throws Exception {
		File oFolder;
		File[] colFile;
		int nCount = 1;
		List<CRDVO> colCRD;
		oFolder = new File(folderName);
		if (oFolder.isDirectory()) {

			PreparedStatement prep = Connection.getInstance().getConnection()
					.prepareStatement("insert into "+Configuration.getInstance().getProperty(Configuration.CONFIGURATION_TABLE) + " (" + "planificacion," + "fid," + "direccion,"
							+ "disco," + "iminolta," + "imagen," + "dia," + "mes," + "fecha," + "hora_pc," + "longitud,"
							+ "latitud," + "altitud," + "heading," + "hora_gps," + "gps_status," + "gps_dilution,"
							+ "satelites," + "linea," + "error," + "geom,anio  ) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			Connection.getInstance().getConnection().setAutoCommit(false);

			colFile = oFolder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return (name.endsWith(".crd") || name.endsWith(".CRD"));

				}
			});

			if (colFile.length == 0) {
				throw new FileNotFoundException();

			}
			for (File oFile : colFile) {
				System.out.println(oFile.getName());
				colCRD = findByFile(oFile);
				updateMessage("Procesando Archivo " + nCount + " de " + colFile.length);
				updateProgress(nCount, colFile.length);
				nCount++;
				Thread.sleep(1000);
				for (CRDVO dtCrdvo : colCRD) {

//					int n = getNextId();
//
//					System.out.println(n);
//					// prep.setInt(1, n);
					prep.setString(1, dtCrdvo.getPlanificacion());
					prep.setInt(2, dtCrdvo.getFid());
					prep.setInt(3, dtCrdvo.getDireccion());
					prep.setInt(4, dtCrdvo.getDisco());
					prep.setInt(5, dtCrdvo.getIminolta());
					prep.setInt(6, dtCrdvo.getImagen());
					prep.setString(7, dtCrdvo.getDia());
					prep.setString(8, dtCrdvo.getMes());
					prep.setInt(9, dtCrdvo.getFecha());
					prep.setTime(10, new Time(dtCrdvo.getHora_pc().getTime()));
					prep.setDouble(11, dtCrdvo.getLongitud());
					prep.setDouble(12, dtCrdvo.getLatitud());
					prep.setDouble(13, dtCrdvo.getAltitud());
					prep.setDouble(14, dtCrdvo.getHeading());
					prep.setTime(15, new Time(dtCrdvo.getHora_gps().getTime()));
					prep.setInt(16, dtCrdvo.getGps_status());
					prep.setDouble(17, dtCrdvo.getGps_dilution());
					prep.setInt(18, dtCrdvo.getSatelites());
					prep.setString(19, dtCrdvo.getLinea());
					prep.setString(20, dtCrdvo.getError());

					prep.setObject(21, dtCrdvo.getGeometry());
					prep.setInt(22, dtCrdvo.getFechaArchivo().get(GregorianCalendar.YEAR));
					prep.addBatch();
					 

				}
				System.out.println(prep.toString());
				int[] count = prep.executeBatch();

				Connection.getInstance().getConnection().commit();

			}
		} else {
			throw new FolderNotFoundException();
		}
		return null;
	}

	private static int getNextId() throws SQLException, ClassNotFoundException {
		if (nextId == -1) {
			Statement stat = Connection.getInstance().getConnection().createStatement();
			ResultSet rs = stat.executeQuery("select MAX(gid) as max from "+Configuration.getInstance().getProperty(Configuration.CONFIGURATION_TABLE));
			rs.next();
			nextId = rs.getInt("max");
		}
		return nextId++;
	}

	private static List<CRDVO> findByFile(File oFile) throws Exception {
		int nLinea = 1;
		String[] colData;
		CRDVO dtCRDVO;
		List<CRDVO> colReturns = new ArrayList<CRDVO>();
		DateFormat formatHoraPc = new SimpleDateFormat("HH:MM:ss.SS");
		DateFormat formatHoraGPS = new SimpleDateFormat("HH:MM:ss");

		GregorianCalendar oGregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		oGregorianCalendar.setTimeInMillis(oFile.lastModified());

		try (BufferedReader br = new BufferedReader(new FileReader(oFile))) {

			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace(" ", ";");
				line = line.replace(";;", ";");
				line = line.replace(";;", ";");
				colData = line.split(";");
//				System.out.println(line+"  "+colData.length);
				if (colData.length != 19 && colData.length != 21 && colData.length != 22) {
					throw new LineaInvalidException("Error la linea: " + nLinea + " en el archivo: " + oFile.getName());
				} 
				dtCRDVO = new CRDVO();

				dtCRDVO.setPlanificacion(colData[0]);
				dtCRDVO.setFid(Integer.parseInt(colData[1]));
				dtCRDVO.setDireccion(Integer.parseInt(colData[2]));
				dtCRDVO.setDisco(Integer.parseInt(colData[3]));
				dtCRDVO.setIminolta(Integer.parseInt(colData[4]));
				dtCRDVO.setImagen(Integer.parseInt(colData[5]));
				dtCRDVO.setDia(colData[6]);
				dtCRDVO.setMes(colData[7]);
				dtCRDVO.setFecha(Integer.parseInt(colData[8]));
				dtCRDVO.setHora_pc(formatHoraPc.parse(colData[9]));
				dtCRDVO.setLongitud(Double.parseDouble(colData[10]));
				dtCRDVO.setLatitud(Double.parseDouble(colData[11]));
				dtCRDVO.setAltitud(Double.parseDouble(colData[12]));
				dtCRDVO.setHeading(Double.parseDouble(colData[13]));
				dtCRDVO.setHora_gps(formatHoraGPS.parse(colData[14]));
				dtCRDVO.setGps_status(Integer.parseInt(colData[15]));
				dtCRDVO.setGps_dilution(Double.parseDouble(colData[16]));
				dtCRDVO.setSatelites(Integer.parseInt(colData[17]));

				if (colData.length != 21) {
					dtCRDVO.setLinea(colData[18]);
				} else {
					dtCRDVO.setLinea(colData[18].replaceAll("!", ""));
					dtCRDVO.setError("!!" + colData[19] + colData[20]);
				}

				dtCRDVO.setFechaArchivo(oGregorianCalendar);
				colReturns.add(dtCRDVO);
				nLinea++;
			}
		}
		return colReturns;
	}
}
