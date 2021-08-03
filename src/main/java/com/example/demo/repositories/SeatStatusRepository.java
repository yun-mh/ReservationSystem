package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.customRepositories.HourInWorkPatternCustomRepository;
import com.example.demo.customRepositories.SeatStatusCustomRepository;
import com.example.demo.entities.HourInWorkPatternEntity;
import com.example.demo.entities.SeatStatusEntity;

public abstract interface SeatStatusRepository extends JpaRepository<SeatStatusEntity, Integer>, SeatStatusCustomRepository<SeatStatusEntity> {
	
	// 故障機予約中のマシンの台数を取得
	@Query(name = "SELECT COUNT(t09_machine_code) FROM t09_seat_status x WHERE x.t09_date = :date AND x.t09_checkin_hour = :hour AND x.t09_machine_code = :machinecode AND x.t09_machine_no IN(SELECT t18_machine_no FROM t18_trouble_machine y WHERE y.t18_machine_code = :machinecode AND NOT(y.t18_state = '0' )) AND x.t09_checkin_flag<>'3'", nativeQuery = true)
	public Integer countTroubledMachineByDateAndCheckinHourAndMachineCode(@Param("date") Date date, @Param("hour") String hour, @Param("machinecode") String machinecode);
	
	// 予約中のマシンの台数を取得
	@Query(name = "SELECT COUNT(t09_machine_code) FROM t09_seat_status x WHERE x.t09_date = :date AND x.t09_checkin_hour = :hour AND x.t09_machine_code = :machinecode AND (x.t09_checkin_flag = '1' OR x.t09_checkin_flag = '2' OR x.t09_checkin_flag = '4')", nativeQuery = true)
	public Integer countReservedMachineByDateAndCheckinHourAndMachineCode(@Param("date") Date date, @Param("hour") String hour, @Param("machinecode") String machinecode);
	
}
