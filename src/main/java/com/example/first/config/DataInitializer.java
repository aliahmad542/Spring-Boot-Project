package com.example.first.config;

import com.example.first.entity.User;
import com.example.first.enums.Role;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setFirstName("مدير");
            admin.setLastName("النظام");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("0501234567");
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);
            System.out.println(" تم إنشاء مستخدم المدير");
        }

        if (userRepository.findByEmail("staff@example.com").isEmpty()) {
            User staff = new User();
            staff.setFirstName("موظف");
            staff.setLastName("النظام");
            staff.setEmail("staff@example.com");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setPhone("0507654321");
            staff.setRole(Role.STAFF);
            staff.setActive(true);

            userRepository.save(staff);
            System.out.println(" تم إنشاء مستخدم الموظف");
        }

        if (userRepository.findByEmail("customer@example.com").isEmpty()) {
            User customer = new User();
            customer.setFirstName("عميل");
            customer.setLastName("النظام");
            customer.setEmail("customer@example.com");
            customer.setPassword(passwordEncoder.encode("customer123"));
            customer.setPhone("0509876543");
            customer.setRole(Role.CUSTOMER);
            customer.setActive(true);

            userRepository.save(customer);
            System.out.println("✅ تم إنشاء مستخدم العميل");
        }
    }
}