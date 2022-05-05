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
				System.out.println("** 프로그램을 종료합니다.");
				treatmentPatientService.setFile();
				return;
			default:
				System.out.println("err) 메뉴를 다시 선택하세요");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ 입원 환자 관리 프로그램] ===");
		System.out.println("        1. 환자 등록");
		System.out.println("        2. 전체 출력");
		System.out.println("        3. 환자번호로 조회");
		System.out.println("        4. 환자이름으로 조회");
		System.out.println("        0. 종  료");
		System.out.println("---------------------------");
		System.out.print("       선택> ");
	}

	// 환자 번호로 검색
	private void searchPatientById() {
		System.out.println("### 환자 조회(번호) ###");
		System.out.print("> 환자 번호 : ");
		String patientId = scanner.next();
		
		Patient p = treatmentPatientService.findByPatientId(patientId);
		if(p != null) {
			System.out.println(" ** 조회  결과 ");
			System.out.println("> 환자 번호 : " + p.getPatientId());
			System.out.println("> 이    름 : " + p.getName());
			System.out.println("> 나    이 : " + p.getAge());
		}
		else System.out.println("** 환자가 존재하지 않습니다.");return;
		
	}

	// 환자 이름으로 검색
	private void searchPatientByName() {
		System.out.println("### 환자 조회(이름) ###");
		System.out.print("> 환자 이름 : ");
		String name = scanner.next();
			
		List<Patient> searchedByNameList = treatmentPatientService.findByPatientName(name);
		if(!searchedByNameList.isEmpty()) {
			System.out.println("\n** 조회 결과");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
			System.out.println("--------------------------------------------------------------------");
			searchedByNameList.forEach(i->System.out.println(i));
		}
		else System.out.println("** 환자가 존재하지 않습니다.");return;
		
	}

	// 입원 환자 전체 정보 출력
	private void printAll() {
		List<Patient> list = treatmentPatientService.selectAll();
		if(list == null) {
			System.out.println("등록된 환자가 없습니다.");
			return;
		}
		System.out.println("                      << 병원 관리 프로그램 >> ");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
		System.out.println("--------------------------------------------------------------------");
		int expenses = 0; // 총 진찰비 합계
		int hospitalBill = 0; // 총 입원비 합계
		int total = 0; // 총 진료비 합계
		
		Collections.sort(list, (o1, o2) -> o1.getPatientId().compareTo(o2.getPatientId()));
		list.forEach(i -> System.out.println(i));
		for(Patient p : list) {
			expenses += p.getExpenses();
			hospitalBill += p.getHospitalBill();
			total += p.total();
		}
				
		System.out.println("--------------------------------------------------------------------");
		System.out.println("* 총 진찰비 : " + expenses);
		System.out.println("* 총 입원비 : " + hospitalBill);
		System.out.println("* 총 진료비 : " + total);
		System.out.println();
	}

	// 입원 환자 정보 등록
	private void regist() {
		// 환자 번호 입력
		System.out.println("### 환자 정보 입력 ###");
		System.out.print(">환자 번호 : ");
		String patientId = scanner.next(); // 환자 번호

		// 환자 정보 조회
		Patient patient = treatmentPatientService.findByPatientId(patientId);

		// 나이 및 이름 입력
		String name = getPatientName(patient); // 이름
		int age = getPatientAge(patient); // 나이

		// 진료과 입력
		Department department = inputPart();

		System.out.print(">입원 일수: ");
		int period = scanner.nextInt();
		boolean isRegistered = treatmentPatientService.regist(new Patient(patientId, name, age, department, period));
		if (isRegistered) {
			System.out.println("** 환자가 등록되었습니다.");
		} else {
			System.out.println("** 환자 등록에 실패하였습니다.");
		}
	}

	// 입원할 진료과목 선택
	private Department inputPart() {
		// 진료과목을 잘못 입력하면 제대로 입력할 때까지 반복적으로 입력받는다.
		while (true) {
			System.out.println("** 진료받는 진료과를 선택하세요");
			System.out.print("1) 외과  2) 내과  3) 피부과  4) 소아과  5) 산부인과  6)  비뇨기과 ");
			int departmentNumber = scanner.nextInt();
			try {
				return Department.of(departmentNumber);
			} catch (IllegalArgumentException e) {
				System.out.println("목록중에서 선택하세요");
			}
		}
	}

	private String getPatientName(Patient patient) {
		if (patient != null) {
			String name = patient.getName();
			System.out.print(">이름: " + name);
			return name;
		}

		System.out.print(">이름: ");
		return scanner.next();
	}

	private int getPatientAge(Patient patient) {
		if (patient != null) {
			int age = patient.getAge();
			System.out.print(">나이: " + age);
			return age;
		}

		System.out.print(">나이: ");
		return scanner.nextInt();
	}
}
