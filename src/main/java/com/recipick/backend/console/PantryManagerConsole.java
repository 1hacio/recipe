package com.recipick.backend.console;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Scanner;

public class PantryManagerConsole {
    private static PantryDAO pantryDAO = new PantryDAO();
    private static UserDAO userDAO = new UserDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== 시스템 메뉴 ====");
            System.out.println("1. 식자재 재고 관리");
            System.out.println("2. 사용자 관리");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1 -> pantryMenu();
                case 2 -> userMenu();
                case 0 -> {
                    System.out.println("시스템을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void pantryMenu() {
        while (true) {
            System.out.println("\n==== 식자재 재고 관리 ====");
            System.out.println("1. 재고 목록 조회");
            System.out.println("2. 재고 추가");
            System.out.println("3. 재고 수정");
            System.out.println("4. 재고 삭제");
            System.out.println("0. 돌아가기");
            System.out.print("메뉴 선택: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewAllItems();
                case 2 -> addItem();
                case 3 -> updateItem();
                case 4 -> deleteItem();
                case 0 -> {
                    return;
                }
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n==== 사용자 관리 ====");
            System.out.println("1. 사용자 목록 조회");
            System.out.println("2. 사용자 추가");
            System.out.println("3. 사용자 삭제");
            System.out.println("0. 돌아가기");
            System.out.print("메뉴 선택: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    List<User> users = userDAO.getAllUsers();
                    System.out.printf("%-5s %-20s %-15s %-10s\n", "ID", "이메일", "닉네임", "OAuth");
                    for (User u : users) {
                        System.out.printf("%-5d %-20s %-15s %-10s\n",
                            u.getId(), u.getEmail(), u.getNickname(), u.isOauth() ? "O" : "X");
                    }
                }
                case 2 -> {
                    User user = new User();
                    System.out.print("이메일: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("닉네임: ");
                    user.setNickname(scanner.nextLine());
                    System.out.print("OAuth 사용자입니까? (true/false): ");
                    user.setOauth(Boolean.parseBoolean(scanner.nextLine()));
                    userDAO.insertUser(user);
                    System.out.println("사용자 추가 완료");
                }
                case 3 -> {
                    System.out.print("삭제할 사용자 ID: ");
                    long id = Long.parseLong(scanner.nextLine());
                    userDAO.deleteUser(id);
                    System.out.println("사용자 삭제 완료");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void viewAllItems() {
        List<PantryItem> items = pantryDAO.getAllItems();
        System.out.printf("%-5s %-15s %-10s %-10s %-15s\n", "ID", "재료명", "수량", "단위", "유통기한");
        for (PantryItem item : items) {
            System.out.printf("%-5d %-15s %-10.2f %-10s %-15s\n",
                item.getId(),
                item.getIngredientName(),
                item.getQuantity(),
                item.getUnit(),
                new SimpleDateFormat("yyyy-MM-dd").format(item.getExpiryDate())
            );
        }
    }

    private static void addItem() {
        PantryItem item = new PantryItem();
        System.out.print("재료명: ");
        item.setIngredientName(scanner.nextLine());
        System.out.print("수량: ");
        item.setQuantity(Double.parseDouble(scanner.nextLine()));
        System.out.print("단위: ");
        item.setUnit(scanner.nextLine());
        System.out.print("유통기한 (yyyy-MM-dd): ");
        try {
            item.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine()));
            pantryDAO.insertItem(item);
            System.out.println("재고가 추가되었습니다.");
        } catch (Exception e) {
            System.out.println("날짜 형식 오류: " + e.getMessage());
        }
    }

    private static void updateItem() {
        System.out.print("수정할 ID: ");
        long id = Long.parseLong(scanner.nextLine());

        PantryItem item = new PantryItem();
        item.setId(id);
        System.out.print("재료명: ");
        item.setIngredientName(scanner.nextLine());
        System.out.print("수량: ");
        item.setQuantity(Double.parseDouble(scanner.nextLine()));
        System.out.print("단위: ");
        item.setUnit(scanner.nextLine());
        System.out.print("유통기한 (yyyy-MM-dd): ");
        try {
            item.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine()));
            pantryDAO.updateItem(item);
            System.out.println("재고가 수정되었습니다.");
        } catch (Exception e) {
            System.out.println("날짜 형식 오류: " + e.getMessage());
        }
    }

    private static void deleteItem() {
        System.out.print("삭제할 ID: ");
        long id = Long.parseLong(scanner.nextLine());
        pantryDAO.deleteItem(id);
        System.out.println("재고가 삭제되었습니다.");
    }
}
