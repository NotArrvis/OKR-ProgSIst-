CREATE TABLE objectives (
                            id SERIAL PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description TEXT,
                            completion_percentage DECIMAL(5,2) DEFAULT 0.0
);

CREATE TABLE key_results (
                             id SERIAL PRIMARY KEY,
                             description TEXT NOT NULL,
                             goal TEXT NOT NULL,
                             completion_percentage DECIMAL(5,2) DEFAULT 0.0,
                             objective_id INT NOT NULL,
                             CONSTRAINT fk_objective FOREIGN KEY (objective_id) REFERENCES objectives(id) ON DELETE CASCADE
);

CREATE TABLE initiatives (
                             id SERIAL PRIMARY KEY,
                             title VARCHAR(255) NOT NULL,
                             description TEXT,
                             completion_percentage DECIMAL(5,2) DEFAULT 0.0,
                             key_result_id INT NOT NULL,
                             CONSTRAINT fk_key_result FOREIGN KEY (key_result_id) REFERENCES key_results(id) ON DELETE CASCADE
);
