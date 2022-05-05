package net.scit.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.scit.entity.Patient;

public class TreatmentPatientServiceImpl implements TreatmentPatientService {
	List<Patient> list = new ArrayList<Patient>();

	public TreatmentPatientServiceImpl() {
		getFile();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getFile() {
		File file = null;
		ObjectInputStream ois = null;

		file = new File("patient.dat");
		if (!file.exists())
			return;

		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			list = (List<Patient>) ois.readObject();

			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setFile() {
		File file = null;
		ObjectOutputStream oos = null;

		try {
			file = new File("patient.dat");
			oos = new ObjectOutputStream(new FileOutputStream(file));

			oos.writeObject(list);

			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean regist(Patient patient) {
		boolean result = list.add(patient);
		if (result)
			return true;
		return false;
	}

	@Override
	public Patient findByPatientId(String patientId) {
		for (Patient patient : list) {
			if (temp.getPatientId().equals(patientId)) {
				return patient;
			}
		}
		return null;
	}

	@Override
	public List<Patient> findByPatientName(String name) {
		List<Patient> patientlist = new ArrayList<>();
		for (Patient patient : list) {
			if (patient.getName().equals(name)){
				patientlist.add(patient);
		}
		return patientlist;
	}

	@Override
	public List<Patient> selectAll() {
		return list;
	}

}
