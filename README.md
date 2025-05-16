Book My Show - Backend (In-Memory Version)
This project is a simple backend simulation of the Book My Show application, designed using Java and in-memory data structures (no external database used).
It provides core functionalities like show registration, slot management, ticket booking, and cancellation via RESTful APIs.


1. ✅  Registers a show with its genre.
       POST http://localhost:8081/registerShow?name=Arijit Singh&genre=Singing

2. 🕒 Creates time slots for a specific show with available seat counts per slot.
       POST http://localhost:8081/onboardShowSlots?name=Arijit Singh&09:00-10:00=4&12:00-13:00=2&10:00-11:00=1&11:00-12:00=3&14:00-15:00=2

3. 🔍 Search Shows by Genre
      GET http://localhost:8081/showAvailByGenre?genre=Comedy

4. 🎫 Books a ticket for a show, time slot, and number of seats. Returns a unique booking ID.
       POST http://localhost:8081/bookTicket?user=UserB&show=TMKOC&time=09:00-10:00&count=4

5. ❌ Cancels a previously booked ticket using the booking ID.
       POST http://localhost:8081/cancelBooking?id=3cbc87a8-a5d5-443d-bde1-e4ba5956749d



   

