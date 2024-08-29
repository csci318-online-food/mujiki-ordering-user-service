CREATE TYPE IF NOT EXISTS user_roles AS ENUM ('ADMIN', 'USER', 'RESTAURANT');
CREATE TYPE IF NOT EXISTS cuisine_type AS ENUM (
    'CHINESE',
    'JAPANESE',
    'KOREAN',
    'THAI',
    'VIETNAMESE',
    'INDIAN',
    'ITALIAN',
    'MEXICAN',
    'AMERICAN',
    'FRENCH',
    'GREEK',
    'SPANISH',
    'GERMAN',
    'BRAZILIAN',
    'ARGENTINIAN',
    'PERUVIAN',
    'CUBAN',
    'EGYPTIAN',
    'MOROCCAN',
    'TURKISH'
    );


CREATE TABLE IF NOT EXISTS users (
 id UUID PRIMARY KEY,
 username varchar(64),
 password varchar(256),
 firstname varchar(16),
 lastname varchar(16),
 email varchar(64),
 phone varchar(16),
 roles user_roles NOT NULL,
 create_at timestamp,
 modify_at timestamp,
 modify_by varchar(64),
 create_by varchar(64)
);
