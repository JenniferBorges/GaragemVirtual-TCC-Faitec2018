CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    street TEXT NOT NULL,
    number TEXT NOT NULL,
    neighborhood TEXT NOT NULL,
    city TEXT NOT NULL,
    state TEXT NOT NULL,
    zip TEXT NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    UNIQUE (street, number, neighborhood, city, state, zip, longitude, latitude)
);

CREATE TABLE _user (
    id SERIAL PRIMARY KEY,
    identity TEXT,
    name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    gender TEXT CHECK (gender IN ('M', 'F')),
    active BOOLEAN NOT NULL DEFAULT false,  
    banned BOOLEAN NOT NULL DEFAULT false,
    auth_token TEXT,
    reset_token TEXT,
    reset_complete BOOLEAN NOT NULL DEFAULT false,
    joining_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT Now(),
    is_admin BOOLEAN NOT NULL DEFAULT false,
    is_auth BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE user_phone (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES _user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    number TEXT NOT NULL
);

CREATE TABLE garage (
    id SERIAL PRIMARY KEY,
    address_id INTEGER NOT NULL REFERENCES address(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    user_id INTEGER NOT NULL REFERENCES _user(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    height DOUBLE PRECISION NOT NULL,
    width DOUBLE PRECISION NOT NULL,
    length DOUBLE PRECISION NOT NULL,
    access TEXT NOT NULL,
    has_roof BOOLEAN NOT NULL,
    has_cam BOOLEAN NOT NULL,
    has_indemnity BOOLEAN NOT NULL,
    has_electronic_gate BOOLEAN NOT NULL
);

CREATE TABLE advertisement (
    id SERIAL PRIMARY KEY,
    garage_id INTEGER NOT NULL REFERENCES garage(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL DEFAULT 0,
    currency TEXT NOT NULL DEFAULT 'R$',
    active BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE disponibility (
    id SERIAL PRIMARY KEY,
    advertisement_id INTEGER NOT NULL REFERENCES advertisement(id) ON UPDATE CASCADE ON DELETE CASCADE,
    day INTEGER NOT NULL CHECK (day BETWEEN 1 AND 7),
    starts_at TIME NOT NULL,
    ends_at TIME NOT NULL
);

CREATE TABLE vehicle (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES _user(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    plate TEXT NOT NULL UNIQUE,
    type TEXT,
    manufacturer TEXT,
    model TEXT,
    year TEXT,
    color TEXT,
    chassis TEXT,
    is_auth BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE rent_garage (
    id SERIAL PRIMARY KEY,
    advertisement_id INTEGER NOT NULL REFERENCES advertisement(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    vehicle_id INTEGER NOT NULL REFERENCES vehicle(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT Now(),
    is_auth BOOLEAN,
    initial_date_time TIMESTAMP WITHOUT TIME ZONE,
    final_date_time TIMESTAMP WITHOUT TIME ZONE, 
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    message TEXT
);

CREATE TABLE rating (
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    rent_garage_id INTEGER NOT NULL REFERENCES rent_garage(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    user_id INTEGER REFERENCES _user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    message TEXT,
    PRIMARY KEY(rent_garage_id, user_id)
);

CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    user_to_id INTEGER NOT NULL REFERENCES _user(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    user_from_id INTEGER NOT NULL REFERENCES _user(id) ON UPDATE CASCADE ON DELETE NO ACTION,  
    rent_garage_id INTEGER NOT NULL REFERENCES rent_garage(id) ON UPDATE CASCADE ON DELETE NO ACTION,
    date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT Now(),
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE image (
    src TEXT PRIMARY KEY,
    user_id INTEGER REFERENCES _user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    garage_id INTEGER REFERENCES garage(id) ON UPDATE CASCADE ON DELETE CASCADE,    
    vehicle_id INTEGER REFERENCES vehicle(id) ON UPDATE CASCADE ON DELETE CASCADE,
    type INTEGER NOT NULL
);