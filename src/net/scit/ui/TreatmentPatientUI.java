package net.scit.ui;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import net.scit.entity.Department;
import net.scit.entity.Patient;
import net.scit.service.TreatmentPatientService;
import net.scit.service.TreatmentPatientServiceImpl;

public class TreatmentPatientUI {
	private Scanner scanner = new Scanner(System.in);
	private TreatmentPatientService treatmentPatientService = new TreatmentPatientServiceImpl();

	public TreatmentPatientUI() {
		String choice;

		while (true) {
			menu();
			choice = scanner.next();

			switch (choice) {
			case "1":
				regist();
				break;
			case "2":
				printAll();
				break;
			case "3":
				searchPatientById();
				break;
			case "4":
				searchPatientByName();
				break;
			case "0":
				System.out.println("** ���α׷��� �����մϴ�.");
				treatmentPatientService.setFile();
				return;
			default:
				System.out.println("err) �޴��� �ٽ� �����ϼ���");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ �Կ� ȯ�� ���� ���α׷�] ===");
		System.out.println("        1. ȯ�� ���");
		System.out.println("        2. ��ü ���");
		System.out.println("        3. ȯ�ڹ�ȣ�� ��ȸ");
		System.out.println("        4. ȯ���̸����� ��ȸ");
		System.out.println("        0. ��  ��");
		System.out.println("---------------------------");
		System.out.print("       ����> ");
	}

	// ȯ�� ��ȣ�� �˻�
	private void searchPatientById() {
		System.out.println("### ȯ�� ��ȸ(��ȣ) ###");
		System.out.print("> ȯ�� ��ȣ : ");
		String patientId = scanner.next();
		
		Patient p = treatmentPatientService.findByPatientId(patientId);
		if(p != null) {
			System.out.println(" ** ��ȸ  ��� ");
			System.out.println("> ȯ�� ��ȣ : " + p.getPatientId());
			System.out.println("> ��    �� : " + p.getName());
			System.out.println("> ��    �� : " + p.getAge());
		}
		else System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");return;
		
	}

	// ȯ�� �̸����� �˻�
	private void searchPatientByName() {
		System.out.println("### ȯ�� ��ȸ(�̸�) ###");
		System.out.print("> ȯ�� �̸� : ");
		String name = scanner.next();
			
		List<Patient> searchedByNameList = treatmentPatientService.findByPatientName(name);
		if(!searchedByNameList.isEmpty()) {
			System.out.println("\n** ��ȸ ���");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
			System.out.println("--------------------------------------------------------------------");
			searchedByNameList.forEach(i->System.out.println(i));
		}
		else System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");return;
		
	}

	// �Կ� ȯ�� ��ü ���� ���
	private void printAll() {
		List<Patient> list = treatmentPatientService.selectAll();
		if(list == null) {
			System.out.println("��ϵ� ȯ�ڰ� �����ϴ�.");
			return;
		}
		System.out.println("                      << ���� ���� ���α׷� >> ");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
		System.out.println("--------------------------------------------------------------------");
		int expenses = 0; // �� ������ �հ�
		int hospitalBill = 0; // �� �Կ��� �հ�
		int total = 0; // �� ����� �հ�
		
		Collections.sort(list, (o1, o2) -> o1.getPatientId().compareTo(o2.getPatientId()));
		list.forEach(i -> System.out.println(i));
		for(Patient p : list) {
			expenses += p.getExpenses();
			hospitalBill += p.getHospitalBill();
			total += p.total();
		}
				
		System.out.println("--------------------------------------------------------------------");
		System.out.println("* �� ������ : " + expenses);
		System.out.println("* �� �Կ��� : " + hospitalBill);
		System.out.println("* �� ����� : " + total);
		System.out.println();
	}

	// �Կ� ȯ�� ���� ���
	private void regist() {
		// ȯ�� ��ȣ �Է�
		System.out.println("### ȯ�� ���� �Է� ###");
		System.out.print(">ȯ�� ��ȣ : ");
		String patientId = scanner.next(); // ȯ�� ��ȣ

		// ȯ�� ���� ��ȸ
		Patient patient = treatmentPatientService.findByPatientId(patientId);

		// ���� �� �̸� �Է�
		String name = getPatientName(patient); // �̸�
		int age = getPatientAge(patient); // ����

		// ����� �Է�
		Department department = inputPart();

		System.out.print(">�Կ� �ϼ�: ");
		int period = scanner.nextInt();
		boolean isRegistered = treatmentPatientService.regist(new Patient(patientId, name, age, department, period));
		if (isRegistered) {
			System.out.println("** ȯ�ڰ� ��ϵǾ����ϴ�.");
		} else {
			System.out.println("** ȯ�� ��Ͽ� �����Ͽ����ϴ�.");
		}
	}

	// �Կ��� ������� ����
	private Department inputPart() {
		// ��������� �߸� �Է��ϸ� ����� �Է��� ������ �ݺ������� �Է¹޴´�.
		while (true) {
			System.out.println("** ����޴� ������� �����ϼ���");
			System.out.print("1) �ܰ�  2) ����  3) �Ǻΰ�  4) �Ҿư�  5) ����ΰ�  6)  �񴢱�� ");
			int departmentNumber = scanner.nextInt();
			try {
				return Department.of(departmentNumber);
			} catch (IllegalArgumentException e) {
				System.out.println("����߿��� �����ϼ���");
			}
		}
	}

	private String getPatientName(Patient patient) {
		if (patient != null) {
			String name = patient.getName();
			System.out.print(">�̸�: " + name);
			return name;
		}

		System.out.print(">�̸�: ");
		return scanner.next();
	}

	private int getPatientAge(Patient patient) {
		if (patient != null) {
			int age = patient.getAge();
			System.out.print(">����: " + age);
			return age;
		}

		System.out.print(">����: ");
		return scanner.nextInt();
	}
}
