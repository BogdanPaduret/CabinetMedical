package com.company.helpers;

import com.company.exceptions.AppointmentFailedException;
import com.company.exceptions.DoctorDoesNotExistException;
import com.company.exceptions.IncorrectDateOrderException;
import com.company.exceptions.PatientDoesNotExistException;
import com.company.models.Appointment;
import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.User;
import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

public final class RepositoryLoad<T> {

    //instance variables
    private static String usersPath="test/com/company/repositories/users";
    private static String appointmentsPath="test/com/company/repositories/appointments";

    //constructor
    private RepositoryLoad() {}

    //initialize repositories
    public static void init(String usersPath, String appointmentsPath) {
        RepositoryLoad.usersPath = usersPath;
        RepositoryLoad.appointmentsPath = appointmentsPath;
        userRepository.load();
        appointmentRepository.load();
    }

    //repositories
    public static final UserRepository userRepository = new UserRepository(RepositoryLoad.usersPath);
    public static final AppointmentRepository appointmentRepository = new AppointmentRepository(RepositoryLoad.appointmentsPath);

    //appointment checker
    public static boolean isAppointmentAvailable(int doctorId, int patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return checkDoctorAndPatientId(doctorId, patientId) && areDatesAvailable(startDate, endDate);
    }

    public static boolean checkDoctorAndPatientId(int doctorId, int patientId) {

        Set<User> users = userRepository.getAll("doctor");
        users.addAll(userRepository.getAll("patient"));

        boolean existsDoctor = false;
        boolean existsPatient = false;

        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user instanceof Doctor doctor) {
                if (doctor.getUserId() == doctorId) {
                    existsDoctor = true;
                }
            }
            if (user instanceof Patient patient) {
                if (patient.getUserId() == patientId) {
                    existsPatient = true;
                }
            }
        }

        return existsDoctor && existsPatient;
    }
    public static boolean areDatesAvailable(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.compareTo(endDate) >= 0) {
            throw new IncorrectDateOrderException();
        }

        boolean startDateAvailable = true;
        boolean endDateAvailable = true;
        boolean isEmpty = true;

        Set<Appointment> appointments = appointmentRepository.getAll();
        Iterator<Appointment> iterator = appointments.iterator();

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            LocalDateTime a = appointment.getStartDate();
            LocalDateTime b = appointment.getEndDate();

            if (a.compareTo(startDate) * startDate.compareTo(b) > 0) {
                startDateAvailable = false;
            }
            if (a.compareTo(endDate) * endDate.compareTo(b) > 0) {
                endDateAvailable = false;
            }

            int sae = startDate.compareTo(a) * a.compareTo(endDate);
            int sbe = startDate.compareTo(b) * b.compareTo(endDate);
            if (sae > 0) {
                isEmpty = false;
            }
            if (sbe > 0) {
                isEmpty = false;
            }
            if (sae == 0 && sbe == 0) {
                isEmpty = false;
            }
        }

        return startDateAvailable && endDateAvailable && isEmpty;
    }

}
