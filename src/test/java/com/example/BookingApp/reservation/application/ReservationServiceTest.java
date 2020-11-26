package com.example.BookingApp.reservation.application;

import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.exception.EntityNotFoundException;
import com.example.BookingApp.reservation.infrastructure.InMemoryRepositoryMock;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserDomainData;
import com.example.BookingApp.user.application.UserMapper;
import com.example.BookingApp.user.application.UserRegistrationData;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    UserService userService;
    RoomService roomService;

    private Reservation reservation = Reservation.builder()
            .id(1L)
            .price(100.00)
            .reservationNumber(12345)
            .accommodationId(1L)
            .roomId(1L)
            .userId(1L)
            .created(LocalDate.now())
            .bookIn(LocalDate.now().plusDays(1))
            .bookOut(LocalDate.now().plusDays(2))
            .build();
    private InMemoryRepositoryMock repositoryMock;

    @BeforeEach()
    public void init() {
        repositoryMock = InMemoryRepositoryMock.newInstance();
        reservationService = new ReservationService(repositoryMock, roomService = Mockito.mock(RoomService.class), userService = Mockito.mock(UserService.class));
    }

    @Test
    void shouldFindReservationByReservationIdAndReturnReservation() {
        //given
        repositoryMock.save(reservation);
        //when
        Reservation reservationFound = reservationService.findById(1L);
        //then
        assertThat(reservationFound.getId()).isSameAs(reservation.getId());
    }

    @Test
    void shouldGiveResultReservationWithIdDoesntExistWhenFindReservationByIdButReservationDoesntExistWithThisId() {
        //given
        repositoryMock.save(reservation);
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> reservationService.findById(2L));
    }

    @Test
    void shouldFindReservationByReservationNumber() {
        //given
        repositoryMock.save(reservation);
        //when
        Reservation ReservationFoundByReservationNumber = reservationService.findByReservationNumber(12345);
        //then
        Assert.assertThat(ReservationFoundByReservationNumber.getReservationNumber(), CoreMatchers.is(12345));
    }

    @Test
    void shouldReturnFirstDateWhenRoomWillBeAvailable() {
        //given
        repositoryMock.save(reservation);
        //when
        LocalDate roomAvailableAt = reservationService.findWhenRoomAvailable(reservation.getRoomId());
        //then
        Assert.assertThat(roomAvailableAt, CoreMatchers.is(LocalDate.now().plusDays(3)));
    }

    @Test
    void shouldReturnListOReservationWhichBookOutDateIsToday() {
        //given
        Reservation firstReservationEndsToday = Reservation.builder()
                .id(2L)
                .bookOut(LocalDate.now())
                .build();

        Reservation secondReservationEndsToday = Reservation.builder()
                .id(3L)
                .bookOut(LocalDate.now())
                .build();

        repositoryMock.save(reservation);
        repositoryMock.save(firstReservationEndsToday);
        repositoryMock.save(secondReservationEndsToday);

        //when
        List<Reservation> allReservationEndsToday = repositoryMock.findAllByBookOutEquals(LocalDate.now());
        //then
        Assert.assertThat(allReservationEndsToday.size(), CoreMatchers.is(2));
        Assert.assertThat(allReservationEndsToday.get(0).getId(), CoreMatchers.is(2L));
        Assert.assertThat(allReservationEndsToday.get(1).getId(), CoreMatchers.is(3L));
    }

    @Test
    void shouldCreateNewReservationFromDataGivenByUser() {
        //given
        UserRegistrationData user = new UserRegistrationData();
        user.setIDNumber(12345);

        User userEntity = new User().toBuilder()
                .id(1L)
                .IDNumber(user.getIDNumber())
                .build();

        UserDomainData userDomainData = UserMapper.mapToDomainUser(userEntity);

        when(userService.findByIDNumber(12345)).thenReturn(userDomainData);
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);

        LocalDate bookIn = LocalDate.now().plusDays(1);
        LocalDate bookOut = LocalDate.now().plusDays(3);

        Room room = new Room();

        //when
        reservationService.makeReservation(user,accommodation.getId(),room, bookIn, bookOut);

        //then
    }

    @Test
    void shouldCancelReservationByChangingReservationStatusToFalse() {
        //given
        reservation.toBuilder()
                .active(true)
                .build();
        repositoryMock.save(reservation);
        //when
        reservationService.cancelReservation(reservation);
        //then
        Assert.assertThat(reservationService.findById(1L).isActive(), CoreMatchers.is(false));
    }

    @Test
    void findAllFreeRoomByUserSearch() {
        //given
        Integer numberOfPersonLookingForAccommodation = 3;
        Double minimumPriceForRoom = 100.00;
        Double maximumPriceForRoom = 300.00;
        LocalDate bookIn = LocalDate.now().plusDays(1);
        LocalDate bookOut = LocalDate.now().plusDays(3);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setName("testAccommodation");

        List<Room> roomList = new ArrayList<>();

        Room firstRoom = new Room();
        firstRoom.setId(1L);
        firstRoom.setMaxPerson(2);
        firstRoom.setPrice(200.00);
        firstRoom.setAccommodationId(1L);
        roomList.add(firstRoom);

        Room secondRoom = new Room();
        secondRoom.setId(2L);
        secondRoom.setMaxPerson(3);
        secondRoom.setPrice(200.00);
        secondRoom.setAccommodationId(1L);
        roomList.add(secondRoom);

        Room thirdRoom = new Room();
        thirdRoom.setId(3L);
        thirdRoom.setMaxPerson(3);
        thirdRoom.setPrice(400.00);
        thirdRoom.setAccommodationId(1L);
        roomList.add(thirdRoom);

        Reservation notReservedRoom = new Reservation().toBuilder()
                .accommodationId(accommodation.getId())
                .active(false).build();

        repositoryMock.save(notReservedRoom);

        //when
        when(roomService.findByUserQuerySearch(minimumPriceForRoom, maximumPriceForRoom, accommodation, numberOfPersonLookingForAccommodation)).thenReturn(roomList);

        List<Room> allFreeRoomByUserSearch = reservationService.findAllFreeRoomByUserSearch(
                numberOfPersonLookingForAccommodation,
                minimumPriceForRoom,
                maximumPriceForRoom,
                accommodation,
                bookIn,
                bookOut);
        //then
        Assert.assertThat(allFreeRoomByUserSearch.get(0), CoreMatchers.is(secondRoom));

    }
}