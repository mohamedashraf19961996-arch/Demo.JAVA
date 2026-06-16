CREATE TABLE IF NOT EXISTS content (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP NULL,
    url VARCHAR(255)
);


Insert Into content (title, description, status, content_type, date_created) VALUES('My Spring Data Blog','A post about spring data','published','blog',NOW());