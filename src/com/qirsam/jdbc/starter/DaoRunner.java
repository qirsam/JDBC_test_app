package com.qirsam.jdbc.starter;

import com.qirsam.jdbc.starter.dao.TicketDao;
import com.qirsam.jdbc.starter.dto.TicketFilter;
import com.qirsam.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {
        var ticket = TicketDao.getInstance().findById(5L);
        System.out.println(ticket);

    }

    private static void filterTest() {
        var ticketFilter = new TicketFilter(3, 2, "Эдуард Щеглов", "A1");
        var tickets = TicketDao.getInstance().findAll(ticketFilter);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void findAllTest() {
        var ticketDao = TicketDao.getInstance();
        var all = ticketDao.findAll();
        for (Ticket ticket : all) {
            System.out.println(ticket);
        }
    }


    private static void findIdTest() {
        var ticketDao = TicketDao.getInstance();
        var mayBeTicket = ticketDao.findById(2L);

        mayBeTicket.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });

        System.out.println(mayBeTicket);
    }

    private static void deleteTest() {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(56L);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassengerNo("123321");
        ticket.setPassengerName("test");
//        ticket.setFlight(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);
        var saveTicket = ticketDao.save(ticket);
        System.out.println(saveTicket);
    }
}
