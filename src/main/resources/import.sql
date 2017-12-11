
use movies_bot;
INSERT INTO messenger_user VALUES ('1414885871972397', 0);

insert into movie_genre(id, name) values(1, 'Comedy');
insert into movie_genre(id, name) values(2, 'Action');
insert into movie_genre(id, name) values(3, 'Thriller');
insert into movie_genre(id, name) values(4, 'Comics');
insert into movie_genre(id, name) values(5, 'Horror');
insert into movie_genre(id, name) values(7, 'Drama');
insert into movie_genre(id, name) values(8, 'Fantazy');

insert into movie(id, image_url, title, trailer_url) values(1, 'https://kinoafisha.ua/upload/2017/01/films/7526/m_1511248210buntar-v-jizni.jpg', 'Rebel in the Rye','https://www.youtube.com/watch?v=VWRhXMMb7CY');
insert into movie_genre_movies(movie_genres_id, movies_id) values(7,1);

insert into movie(id, image_url, title, trailer_url) values(2, 'https://kinoafisha.ua/upload/2016/02/films/7094/m_1511180847liga-spravedlivosti-cast-1.jpg', 'Justice League', 'https://www.youtube.com/watch?v=r9-DM9uBtVI');
insert into movie_genre_movies(movie_genres_id, movies_id) values(8,2);
insert into movie_genre_movies(movie_genres_id, movies_id) values(2,2);
insert into movie_genre_movies(movie_genres_id, movies_id) values(4,2);

insert into movie(id, image_url, title, trailer_url) values(3, 'https://kinoafisha.ua/upload/2017/02/films/7552/m_1490799129valerian-i-gorod-tsyacsi-planet.jpg', 'Valerian and the City of a Thousand Planets', 'https://www.youtube.com/watch?v=W3aOg6_7cEc');
insert into movie_genre_movies(movie_genres_id, movies_id) values(8,3);
insert into movie_genre_movies(movie_genres_id, movies_id) values(4,3);
insert into movie_genre_movies(movie_genres_id, movies_id) values(2,3);

insert into movie(id, image_url, title, trailer_url) values(4, 'https://kinoafisha.ua/upload/2016/10/films/7428/m_1508920858inostranec.jpg', 'The Foreigner', 'https://www.youtube.com/watch?v=om9YCk7ufHs');
insert into movie_genre_movies(movie_genres_id, movies_id) values(7,4);
insert into movie_genre_movies(movie_genres_id, movies_id) values(5,4);
insert into movie_genre_movies(movie_genres_id, movies_id) values(3,4);

insert into cinema(id, image_url, name,longitude,latitude) values(1, 'https://planetakino.ua/f/1/theatres/imax-lviv-forum-mini.jpg', 'Planeta Kino Forum Lviv', 49.8496756,24.0202529);
insert into cinema(id, image_url, name, longitude, latitude) values(2, 'https://planetakino.ua/f/1/theatres/pk-leopolis.jpg', 'Planeta Kino King Cross',49.7729918,24.0074682);

insert into cinema_movies(cinemas_id, movies_id) values(1,1);
insert into cinema_movies(cinemas_id, movies_id) values(1,2);
insert into cinema_movies(cinemas_id, movies_id) values(1,3);

insert into cinema_movies(cinemas_id, movies_id) values(2,2);
insert into cinema_movies(cinemas_id, movies_id) values(2,3);
insert into cinema_movies(cinemas_id, movies_id) values(2,4);

INSERT INTO movie_technology (id, name) VALUES (1, '2D');
INSERT INTO movie_technology (id, name) VALUES (2, '3D');
INSERT INTO movie_technology (id, name) VALUES (3, '4DX');

INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 1, 1);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (1, '15:00', 2, 1, 1);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '19:20', 3, 1, 1);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '23:00', 3, 1, 1);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (10, '00:00', 1, 1, 1);

INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 1, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (1, '15:00', 2, 1, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '19:20', 3, 1, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '23:00', 2, 1, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (10, '00:00', 3, 1, 2);

INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 1, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (1, '15:00', 2, 1, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '19:20', 3, 1, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '23:00', 3, 1, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (10, '00:00', 3, 1, 3);


INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 2, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (1, '15:00', 2, 2, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '19:20', 3, 2, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '23:00', 3, 2, 2);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (2, '00:00', 1, 2, 2);

INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 2, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (10, '15:00', 2, 2, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (20, '19:20', 3, 2, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (20, '23:00', 3, 2, 3);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (12, '00:00', 1, 2, 3);

INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (30, '12:30', 1, 2, 4);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (5, '15:00', 2, 2, 4);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (20, '19:20', 3, 2, 4);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (20, '23:00', 3, 2, 4);
INSERT INTO movie_session (seats_left, session_time, technology_id, cinema_id, movie_id) VALUES (20, '00:00', 3, 2, 4);