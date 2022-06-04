INSERT INTO user_types(id, name, created_at, created_by, updated_at, updated_by)
VALUES
    (0, 'LAWYER', UTC_TIMESTAMP(), NULL, NULL, NULL),
    (1, 'CUSTOMER', UTC_TIMESTAMP(), NULL, NULL, NULL);

INSERT INTO user_states(id, name, created_at, created_by, updated_at, updated_by)
VALUES
    (0, 'Inactive', UTC_TIMESTAMP(), NULL, NULL, NULL),
    (1, 'Active', UTC_TIMESTAMP(), NULL, NULL, NULL);

INSERT INTO lawyer_specializations(id, name, created_at, created_by, updated_at, updated_by)
VALUES
    (0, 'CIVIL LAW', UTC_TIMESTAMP(), NULL, NULL, NULL),
    (1, 'PENAL LAW', UTC_TIMESTAMP(), NULL, NULL, NULL);
    (2, 'CORPORATE LAW', UTC_TIMESTAMP(), NULL, NULL, NULL);
    (3, 'COMMERCIAL LAW', UTC_TIMESTAMP(), NULL, NULL, NULL);