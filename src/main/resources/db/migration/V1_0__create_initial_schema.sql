CREATE TABLE user_types(
    id TINYINT(1) UNSIGNED NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by BINARY(16) NULL,
    updated_at DATETIME NULL,
    updated_by BINARY(16) NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UQ_users_types_name(name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE user_states (
    id TINYINT(1) UNSIGNED NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by BINARY(16) NULL,
    updated_at DATETIME NULL,
    updated_by BINARY(16) NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UQ_users_states_name(name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE lawyer_specializations (
    id TINYINT(1) UNSIGNED NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by BINARY(16) NULL,
    updated_at DATETIME NULL,
    updated_by BINARY(16) NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UQ_lawyer_specialization(name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    username VARCHAR(32) NOT NULL,
    first_name VARCHAR(75) NULL,
    last_name VARCHAR(75) NULL,
    email VARCHAR(150) NOT NULL,
    address VARCHAR(150) NULL,
    university VARCHAR(150) NULL,
    specialization TINYINT(1) UNSIGNED NULL,
    lawyer_price_amount DECIMAL(6,2) NULL,
    lawyer_price_currency VARCHAR(150) NULL,
    user_type_id TINYINT(1) UNSIGNED NOT NULL,
    user_state_id TINYINT(1) UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL,
    created_by BINARY(16) NULL,
    updated_at DATETIME NULL,
    updated_by BINARY(16) NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UQ_users_username(username),
    INDEX IX_users_first_last_name(first_name, last_name),
    INDEX IX_users_users_type_id(client_type_id),
    INDEX IX_users_users_state_id(client_state_id),
    CONSTRAINT FK_users_user_type_id FOREIGN KEY(user_type_id) REFERENCES user_types(id),
    CONSTRAINT FK_users_user_state_id FOREIGN KEY(user_state_id) REFERENCES user_states(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
