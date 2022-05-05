package net.scit.entity;

public enum Department {
	MI(1, "외과"), NI(2, "내과"), SI(3, "피부과"), TI(4, "소아과"), VI(5, "산부인과"), WI(6, "비뇨기과");

	private int code;
	private String description;

	 Department(int code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    public String getDescription() {
	        return this.description;
	    }

	    public int getCode() {
	        return code;
	    }

	    public static Department of(int code) {
	        for (Department department : Department.values()) {
	            if (department.getCode() == code) {
	                return department;
	            }
	        }
	        //찾는 코드가 없는 경우
	        throw new IllegalArgumentException("진료 번호가 올바르지 않습니다.");
	    }

}
