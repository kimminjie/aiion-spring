-- Soccer Service 테이블 생성 스크립트

-- 1. Stadiums 테이블 (가장 먼저 생성)
CREATE TABLE IF NOT EXISTS stadiums (
    id BIGSERIAL PRIMARY KEY,
    stadium_uk VARCHAR(255) UNIQUE NOT NULL,
    stadium_name VARCHAR(255),
    hometeam_uk VARCHAR(255),
    seat_count VARCHAR(255),
    address VARCHAR(500),
    ddd VARCHAR(20),
    tel VARCHAR(50)
);

-- 2. Teams 테이블 (Stadiums 참조)
CREATE TABLE IF NOT EXISTS teams (
    id BIGSERIAL PRIMARY KEY,
    team_uk VARCHAR(255) UNIQUE NOT NULL,
    region_name VARCHAR(255),
    team_name VARCHAR(255),
    e_team_name VARCHAR(255),
    orig_yyyy VARCHAR(20),
    zip_code1 VARCHAR(20),
    zip_code2 VARCHAR(20),
    address VARCHAR(500),
    ddd VARCHAR(20),
    tel VARCHAR(50),
    fax VARCHAR(50),
    homepage VARCHAR(500),
    owner VARCHAR(255),
    stadium_uk VARCHAR(255),
    CONSTRAINT fk_team_stadium FOREIGN KEY (stadium_uk) REFERENCES stadiums(stadium_uk) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 3. Players 테이블 (Teams 참조)
CREATE TABLE IF NOT EXISTS players (
    id BIGSERIAL PRIMARY KEY,
    player_uk VARCHAR(255),
    player_name VARCHAR(255),
    e_player_name VARCHAR(255),
    nickname VARCHAR(255),
    join_yyyy VARCHAR(20),
    position VARCHAR(50),
    back_no VARCHAR(20),
    nation VARCHAR(100),
    birth_date VARCHAR(20),
    solar VARCHAR(20),
    height VARCHAR(20),
    weight VARCHAR(20),
    team_uk VARCHAR(255),
    CONSTRAINT fk_player_team FOREIGN KEY (team_uk) REFERENCES teams(team_uk) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 4. Schedules 테이블 (Stadiums, Teams 참조)
CREATE TABLE IF NOT EXISTS schedules (
    id BIGSERIAL PRIMARY KEY,
    sche_date VARCHAR(20),
    stadium_uk VARCHAR(255),
    gubun VARCHAR(50),
    hometeam_uk VARCHAR(255),
    awayteam_uk VARCHAR(255),
    home_score VARCHAR(20),
    away_score VARCHAR(20),
    CONSTRAINT fk_schedule_stadium FOREIGN KEY (stadium_uk) REFERENCES stadiums(stadium_uk) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_schedule_hometeam FOREIGN KEY (hometeam_uk) REFERENCES teams(team_uk) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_schedule_awayteam FOREIGN KEY (awayteam_uk) REFERENCES teams(team_uk) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 인덱스 생성 (검색 성능 향상)
CREATE INDEX IF NOT EXISTS idx_player_team_uk ON players(team_uk);
CREATE INDEX IF NOT EXISTS idx_player_name ON players(player_name);
CREATE INDEX IF NOT EXISTS idx_team_stadium_uk ON teams(stadium_uk);
CREATE INDEX IF NOT EXISTS idx_team_name ON teams(team_name);
CREATE INDEX IF NOT EXISTS idx_schedule_stadium_uk ON schedules(stadium_uk);
CREATE INDEX IF NOT EXISTS idx_schedule_date ON schedules(sche_date);
CREATE INDEX IF NOT EXISTS idx_stadium_name ON stadiums(stadium_name);
