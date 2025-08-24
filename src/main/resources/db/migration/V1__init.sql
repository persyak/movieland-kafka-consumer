CREATE TABLE IF NOT EXISTS movie_details (
    id SERIAL PRIMARY KEY NOT NULL,
    name_ua varchar(100) NOT NULL,
    name_native VARCHAR(100) NOT NULL,
    year_of_release DATE NOT NULL,
    description VARCHAR(1000) NOT NULL,
    rating double precision NOT NULL,
    price double precision NOT NULL,
    picture_path VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_event (
    id SERIAL PRIMARY KEY NOT NULL,
    movie_event_type varchar(6) NOT NULL,
    movie_details_id INT NOT NULL,
    CONSTRAINT movie_event_movie_details_id
        FOREIGN KEY (movie_details_id)
        REFERENCES movie_details (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS genre (
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_details_genre (
  movie_details_id INT NOT NULL,
  genre_id INT NOT NULL,
  CONSTRAINT movie_details_id
    FOREIGN KEY (movie_details_id)
    REFERENCES movie_details (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT genre_id
    FOREIGN KEY (genre_id)
    REFERENCES genre (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS public.user (
  id SERIAL PRIMARY KEY NOT NULL,
  nickname VARCHAR(45) NOT NULL
  );

CREATE TABLE IF NOT EXISTS review (
  id SERIAL PRIMARY KEY NOT NULL,
  movie_details_id INT NOT NULL,
  user_id INT NOT NULL,
  description VARCHAR(500) NOT NULL,
  CONSTRAINT review_user_id
    FOREIGN KEY (user_id)
    REFERENCES public.user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT review_movie_id
    FOREIGN KEY (movie_details_id)
    REFERENCES movie_details (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS country (
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_details_country (
  movie_details_id INT NOT NULL,
  country_id INT NOT NULL,
  CONSTRAINT movie_country_country_id
    FOREIGN KEY (country_id)
    REFERENCES country (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT movie_country_movie_id
    FOREIGN KEY (movie_details_id)
    REFERENCES movie_details (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);