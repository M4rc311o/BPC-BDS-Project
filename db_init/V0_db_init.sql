CREATE ROLE "bds-user" NOSUPERUSER NOCREATEDB NOCREATEROLE LOGIN ENCRYPTED PASSWORD 'kcVhp234uPDvwaeUq9U';
CREATE DATABASE "library_management_system" OWNER "bds-user";
GRANT CONNECT ON DATABASE "library_management_system" TO "bds-user";
\connect "library_management_system";
DROP SCHEMA IF EXISTS "public" CASCADE;
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON SCHEMAS TO "bds-user";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON FUNCTIONS TO "bds-user";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON ROUTINES TO "bds-user";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON TYPES TO "bds-user";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON TABLES TO "bds-user";
ALTER DEFAULT PRIVILEGES GRANT ALL PRIVILEGES ON SEQUENCES TO "bds-user";
CREATE SCHEMA IF NOT EXISTS "lms" AUTHORIZATION "bds-user";
