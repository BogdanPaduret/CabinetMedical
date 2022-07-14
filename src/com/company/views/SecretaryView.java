package com.company.views;

import com.company.exceptions.AppointmentDoesNotExistException;
import com.company.exceptions.AppointmentFailedException;
import com.company.exceptions.DoctorDoesNotExistException;
import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.Agenda;
import com.company.models.Appointment;
import com.company.models.Doctor;
import com.company.models.Secretary;
import com.company.repositories.Repository;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.*;

public class SecretaryView implements View {

    private Secretary secretary;

    public SecretaryView(Secretary secretary) {
        this.secretary = secretary;
    }

    public void menu() {
        String string = "";

        string += "=== MOD SECRETAR ===";
        string += "\nSunteti logat ca " + secretary.getUserName();
        string += "\nApasati 1 pentru a crea o programare";
        string += "\nApasati 2 pentru a anula o programare";
        string += "\nApasati 3 pentru a modifica o programare";
        string += "\nApasati 9 pentru a vedea toate programarile";
        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }
    @Override
    public void play() {
        this.play("");
    }
    public void play(String input) {
        Scanner scanner = getScanner(input);

        boolean running = true;
        int choice = -1;

        while (running) {

            menu();

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (choice) {
                default -> {}
                case 0 -> running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
                case 1 -> createAppointment(scanner);
                case 2 -> {
                    try {
                        cancelAppointment(scanner);
                        System.out.println("Programarea a fost anulata cu succes.");
                    } catch (Exception e) {
//                        e.printStackTrace();
                        System.out.println("Programarea nu exista. Verificati daca datele introduse sunt corecte.");
                    }
                }
                case 3 -> {
                    try {
                        modifyAppointment(scanner);
                    } catch (Exception e) {
//                        e.printStackTrace();
                        System.out.println("A aparut o eroare. Verificati daca datele introduse sunt corecte.");
                    }
                }
                case 9 -> RepositoryLoad.appointmentRepository.show();
            }

        }

    }

    //helpers
    private void createAppointment(Scanner scanner) {
        createAppointment(scanner, -1);
    }
    private void createAppointment(Scanner scanner,int appointmentId) {
        try {
            RepositoryLoad.appointmentRepository.add(enquireAppointmentDetails(scanner, appointmentId));
        } catch (AppointmentFailedException e) {
            System.out.println(
                    "Programarea nu poate fi facuta cu acest medic in acest interval orar." +
                    "\nAlegeti un alt doctor sau un alt interval orar."
            );
        }

    }

    private Appointment enquireAppointmentDetails(Scanner scanner, int appointmentId) {
        System.out.println("Introduceti numele pacientului si numele doctorului separate prin virgula");
        String[] input = scanner.nextLine().split(STRING_SEPARATOR);
        String patientName = input[0];
        String doctorName = input[1];

        int year = 0;
        int month = 0;
        int day = 0;
        while (year * month * day == 0) {
            System.out.println("Introduceti anul, luna si ziua programarii, separate prin virgula");
            input = scanner.nextLine().split(STRING_SEPARATOR);

            try {
                year = Integer.parseInt(input[0]);
                month = Integer.parseInt(input[1]);
                day = Integer.parseInt(input[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Eroare la introducerea datei. Mai incercati o data");
            }
        }

        int startHour = 0;
        int startMinute=-1;
        while (startHour == 0 || startMinute<0) {
            System.out.println("Introduceti ora si minutul programarii separate prin ':'");
            input = scanner.nextLine().split(":");
            try {
                startHour = Integer.parseInt(input[0]);
                startMinute = Integer.parseInt(input[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Eroare la introducerea orei. Mai incercati o data");
            }
        }

        int duration = 0;
        while (duration == 0) {
            System.out.println("Introduceti durata programarii in minute intregi");
            try {
                duration = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Durata introdusa este incorecta. Mai incercati o data");
            }
        }

        int doctorId = RepositoryLoad.userRepository.getId(doctorName);
        int patientId = RepositoryLoad.userRepository.getId(patientName);
        LocalDateTime startDate = LocalDateTime.of(year, month, day, startHour, startMinute);
        LocalDateTime endDate = startDate.plusMinutes(duration);

        return new Appointment(appointmentId, doctorId, patientId, startDate, endDate);
    }

    private void cancelAppointment(Scanner scanner) {
        boolean isRemoved = false;
        System.out.println("Introduceti numele doctorului sau ID-ul programarii");
        String input = scanner.nextLine();
        int appointmentId = parseInteger(input, -1);
        String successfulCancel = "Programarea a fost anulata cu succes";

        if (appointmentId == -1) {
            String doctorName = input;

            int[] date = new int[0];
            while (date.length < 3 && multiplyIntArray(date) == 0) {
                System.out.println("Introduceti anul, luna si ziua separate prin virgula");
                date = intFromScanner(scanner);
            }

            int[] time = new int[0];
            while (time.length < 2) {
                System.out.println("Introduceti ora si minutul programarii separate prin virgula");
                time = intFromScanner(scanner);
            }

            Doctor doctor = null;
            LocalDateTime startDate = LocalDateTime.of(date[0], date[1], date[2], time[0], time[1]);

            try {
                doctor = (Doctor) RepositoryLoad.userRepository.get(RepositoryLoad.userRepository.getId(doctorName));
            } catch (ClassCastException e) {
                e.printStackTrace();
                System.out.println("Numele introdus nu este al unui doctor");
            }

            Agenda agenda = new Agenda(RepositoryLoad.userRepository.getPath(), RepositoryLoad.appointmentRepository.getPath());

            Iterator<Appointment> iterator = agenda.getDoctorAppointments(doctor).iterator();
            if (doctor != null) {
                while (iterator.hasNext()) {
                    Appointment appointment = iterator.next();
                    if (appointment.getStartDate().equals(startDate)) {
                        RepositoryLoad.appointmentRepository.remove(appointment);
                        isRemoved = true;
                    }
                }
            }
        } else {
            cancelAppointment(appointmentId);
            isRemoved = true;
        }
        if (!isRemoved) {
            throw new AppointmentDoesNotExistException();
        }
    }
    private void cancelAppointment(int id) {
        RepositoryLoad.appointmentRepository.remove(RepositoryLoad.appointmentRepository.get(id));
    }

    private void modifyAppointment(Scanner scanner) {
        System.out.println("Introduceti ID-ul programarii");
        int appointmentId = parseInteger(scanner.nextLine(), -1);

        if (appointmentId != -1) {
            modifyAppointment(appointmentId,scanner);
        } else {
            throw new AppointmentDoesNotExistException();
        }
    }
    private void modifyAppointment(int appointmentId, Scanner scanner) {
        Appointment appointment = enquireAppointmentDetails(scanner, appointmentId);
        try {
            RepositoryLoad.appointmentRepository.set(appointmentId, appointment);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare la modificare.");
        }
    }


    //todo foloseste urmatoarea metoda pentru a simplifica metoda createAppointment()
    private int[] intFromScanner(Scanner scanner) {
        return intFromScanner(scanner, 0);
    }
    private int[] intFromScanner(Scanner scanner, int valOnException) {
        String[] input = scanner.nextLine().split(STRING_SEPARATOR);
        int[] inputArray = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            inputArray[i] = parseInteger(input[i], valOnException);
        }
        return inputArray;
    }

    private int multiplyIntArray(int[] array) {
        int r;
        if (array.length > 0) {
            r = 1;
            for (int i = 0; i < array.length; i++) {
                r = r * array[i];
            }
        } else {
            r = 0;
        }
        return r;
    }

    private int parseInteger(String string, int valueOnException) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return valueOnException;
        }
    }

}
