--changeset moran:create_test_data_table
CREATE TABLE IF NOT EXISTS my_first_table
(
    id              BIGSERIAL                     NOT NULL,
    first_name      TEXT                          NOT NULL,
    last_name       TEXT                          NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW()
)