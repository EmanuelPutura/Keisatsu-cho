--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: AbstractPaper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."AbstractPaper" (
    "PaperID" integer NOT NULL,
    "Description" character varying(256)
);


ALTER TABLE public."AbstractPaper" OWNER TO postgres;

--
-- Name: Account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Account" (
    "AccountID" integer NOT NULL,
    "Email" character varying(64),
    "Username" character varying(64),
    "PasswordDigest" character varying(64),
    "FirstName" character varying(64),
    "LastName" character varying(64),
    "Address" character varying(64),
    "BirthDate" date
);


ALTER TABLE public."Account" OWNER TO postgres;

--
-- Name: Account_AccountID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Account_AccountID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Account_AccountID_seq" OWNER TO postgres;

--
-- Name: Account_AccountID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Account_AccountID_seq" OWNED BY public."Account"."AccountID";


--
-- Name: Author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Author" (
    "AuthorID" integer NOT NULL
);


ALTER TABLE public."Author" OWNER TO postgres;

--
-- Name: Author_AuthorID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Author_AuthorID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Author_AuthorID_seq" OWNER TO postgres;

--
-- Name: Author_AuthorID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Author_AuthorID_seq" OWNED BY public."Author"."AuthorID";


--
-- Name: Chair; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Chair" (
    "ChairID" integer NOT NULL
);


ALTER TABLE public."Chair" OWNER TO postgres;

--
-- Name: ChairPaperEvaluation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ChairPaperEvaluation" (
    "ChairID" integer,
    "PaperID" integer NOT NULL,
    "IsAccepted" boolean
);


ALTER TABLE public."ChairPaperEvaluation" OWNER TO postgres;

--
-- Name: Chair_ChairID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Chair_ChairID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Chair_ChairID_seq" OWNER TO postgres;

--
-- Name: Chair_ChairID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Chair_ChairID_seq" OWNED BY public."Chair"."ChairID";


--
-- Name: Conference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Conference" (
    "ConferenceID" integer NOT NULL,
    "Name" character varying(64),
    url character varying(64),
    "MainOrganiserID" integer,
    "DeadlinesID" integer NOT NULL
);


ALTER TABLE public."Conference" OWNER TO postgres;

--
-- Name: ConferenceDeadlines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ConferenceDeadlines" (
    "DeadlinesID" integer NOT NULL,
    "PaperSubmissionDeadline" date,
    "PaperReviewDeadline" date,
    "AcceptanceNotificationDeadline" date,
    "AcceptedPaperUploadDeadline" date
);


ALTER TABLE public."ConferenceDeadlines" OWNER TO postgres;

--
-- Name: ConferenceDetails_DeadlinesID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."ConferenceDetails_DeadlinesID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ConferenceDetails_DeadlinesID_seq" OWNER TO postgres;

--
-- Name: ConferenceDetails_DeadlinesID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."ConferenceDetails_DeadlinesID_seq" OWNED BY public."ConferenceDeadlines"."DeadlinesID";


--
-- Name: ConferenceSubtitle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ConferenceSubtitle" (
    "ConferenceID" integer,
    "SubtitleID" integer NOT NULL
);


ALTER TABLE public."ConferenceSubtitle" OWNER TO postgres;

--
-- Name: ConferenceTopic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ConferenceTopic" (
    "ConferenceID" integer,
    "TopicID" integer NOT NULL
);


ALTER TABLE public."ConferenceTopic" OWNER TO postgres;

--
-- Name: Conference_ConferenceID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Conference_ConferenceID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Conference_ConferenceID_seq" OWNER TO postgres;

--
-- Name: Conference_ConferenceID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Conference_ConferenceID_seq" OWNED BY public."Conference"."ConferenceID";


--
-- Name: Paper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Paper" (
    "PaperID" integer NOT NULL,
    "Title" character varying(64),
    "Keywords" character varying(128),
    "TopicID" integer,
    "Format" character varying(32) NOT NULL,
    "File" bytea NOT NULL
);


ALTER TABLE public."Paper" OWNER TO postgres;

--
-- Name: PaperAuthor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."PaperAuthor" (
    "AuthorID" integer,
    "PaperID" integer NOT NULL
);


ALTER TABLE public."PaperAuthor" OWNER TO postgres;

--
-- Name: PaperConference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."PaperConference" (
    "PaperID" integer,
    "ConferenceID" integer NOT NULL,
    "AssigneeID" integer
);


ALTER TABLE public."PaperConference" OWNER TO postgres;

--
-- Name: Paper_PaperID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Paper_PaperID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Paper_PaperID_seq" OWNER TO postgres;

--
-- Name: Paper_PaperID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Paper_PaperID_seq" OWNED BY public."Paper"."PaperID";


--
-- Name: Reviewer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Reviewer" (
    "ReviewerID" integer NOT NULL
);


ALTER TABLE public."Reviewer" OWNER TO postgres;

--
-- Name: ReviewerAssignedPaper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ReviewerAssignedPaper" (
    "ReviewerID" integer,
    "PaperID" integer NOT NULL,
    "Evaluation" character varying(256) NOT NULL,
    "Comment" character varying(256) NOT NULL
);


ALTER TABLE public."ReviewerAssignedPaper" OWNER TO postgres;

--
-- Name: ReviewerPaperBid; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ReviewerPaperBid" (
    "ReviewerID" integer,
    "PaperID" integer NOT NULL
);


ALTER TABLE public."ReviewerPaperBid" OWNER TO postgres;

--
-- Name: ReviewerTopic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ReviewerTopic" (
    "ReviewerID" integer,
    "TopicID" integer NOT NULL
);


ALTER TABLE public."ReviewerTopic" OWNER TO postgres;

--
-- Name: Reviewer_ReviewerID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Reviewer_ReviewerID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Reviewer_ReviewerID_seq" OWNER TO postgres;

--
-- Name: Reviewer_ReviewerID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Reviewer_ReviewerID_seq" OWNED BY public."Reviewer"."ReviewerID";


--
-- Name: Subtitle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Subtitle" (
    "SubtitleID" integer NOT NULL,
    "Language" character varying(64)
);


ALTER TABLE public."Subtitle" OWNER TO postgres;

--
-- Name: Subtitle_SubtitleID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Subtitle_SubtitleID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Subtitle_SubtitleID_seq" OWNER TO postgres;

--
-- Name: Subtitle_SubtitleID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Subtitle_SubtitleID_seq" OWNED BY public."Subtitle"."SubtitleID";


--
-- Name: TopicOfInterest; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."TopicOfInterest" (
    "TopicID" integer NOT NULL,
    "Name" character varying(64)
);


ALTER TABLE public."TopicOfInterest" OWNER TO postgres;

--
-- Name: TopicOfInterest_TopicID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."TopicOfInterest_TopicID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."TopicOfInterest_TopicID_seq" OWNER TO postgres;

--
-- Name: TopicOfInterest_TopicID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."TopicOfInterest_TopicID_seq" OWNED BY public."TopicOfInterest"."TopicID";


--
-- Name: Account AccountID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Account" ALTER COLUMN "AccountID" SET DEFAULT nextval('public."Account_AccountID_seq"'::regclass);


--
-- Name: Author AuthorID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Author" ALTER COLUMN "AuthorID" SET DEFAULT nextval('public."Author_AuthorID_seq"'::regclass);


--
-- Name: Chair ChairID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Chair" ALTER COLUMN "ChairID" SET DEFAULT nextval('public."Chair_ChairID_seq"'::regclass);


--
-- Name: Conference ConferenceID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Conference" ALTER COLUMN "ConferenceID" SET DEFAULT nextval('public."Conference_ConferenceID_seq"'::regclass);


--
-- Name: ConferenceDeadlines DeadlinesID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceDeadlines" ALTER COLUMN "DeadlinesID" SET DEFAULT nextval('public."ConferenceDetails_DeadlinesID_seq"'::regclass);


--
-- Name: Paper PaperID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Paper" ALTER COLUMN "PaperID" SET DEFAULT nextval('public."Paper_PaperID_seq"'::regclass);


--
-- Name: Reviewer ReviewerID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reviewer" ALTER COLUMN "ReviewerID" SET DEFAULT nextval('public."Reviewer_ReviewerID_seq"'::regclass);


--
-- Name: Subtitle SubtitleID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Subtitle" ALTER COLUMN "SubtitleID" SET DEFAULT nextval('public."Subtitle_SubtitleID_seq"'::regclass);


--
-- Name: TopicOfInterest TopicID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."TopicOfInterest" ALTER COLUMN "TopicID" SET DEFAULT nextval('public."TopicOfInterest_TopicID_seq"'::regclass);


--
-- Data for Name: AbstractPaper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."AbstractPaper" ("PaperID", "Description") FROM stdin;
\.


--
-- Data for Name: Account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Account" ("AccountID", "Email", "Username", "PasswordDigest", "FirstName", "LastName", "Address", "BirthDate") FROM stdin;
\.


--
-- Data for Name: Author; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Author" ("AuthorID") FROM stdin;
\.


--
-- Data for Name: Chair; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Chair" ("ChairID") FROM stdin;
\.


--
-- Data for Name: ChairPaperEvaluation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ChairPaperEvaluation" ("ChairID", "PaperID", "IsAccepted") FROM stdin;
\.


--
-- Data for Name: Conference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Conference" ("ConferenceID", "Name", url, "MainOrganiserID", "DeadlinesID") FROM stdin;
\.


--
-- Data for Name: ConferenceDeadlines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ConferenceDeadlines" ("DeadlinesID", "PaperSubmissionDeadline", "PaperReviewDeadline", "AcceptanceNotificationDeadline", "AcceptedPaperUploadDeadline") FROM stdin;
\.


--
-- Data for Name: ConferenceSubtitle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ConferenceSubtitle" ("ConferenceID", "SubtitleID") FROM stdin;
\.


--
-- Data for Name: ConferenceTopic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ConferenceTopic" ("ConferenceID", "TopicID") FROM stdin;
\.


--
-- Data for Name: Paper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Paper" ("PaperID", "Title", "Keywords", "TopicID", "Format", "File") FROM stdin;
\.


--
-- Data for Name: PaperAuthor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."PaperAuthor" ("AuthorID", "PaperID") FROM stdin;
\.


--
-- Data for Name: PaperConference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."PaperConference" ("PaperID", "ConferenceID", "AssigneeID") FROM stdin;
\.


--
-- Data for Name: Reviewer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Reviewer" ("ReviewerID") FROM stdin;
\.


--
-- Data for Name: ReviewerAssignedPaper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ReviewerAssignedPaper" ("ReviewerID", "PaperID", "Evaluation", "Comment") FROM stdin;
\.


--
-- Data for Name: ReviewerPaperBid; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ReviewerPaperBid" ("ReviewerID", "PaperID") FROM stdin;
\.


--
-- Data for Name: ReviewerTopic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."ReviewerTopic" ("ReviewerID", "TopicID") FROM stdin;
\.


--
-- Data for Name: Subtitle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Subtitle" ("SubtitleID", "Language") FROM stdin;
\.


--
-- Data for Name: TopicOfInterest; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."TopicOfInterest" ("TopicID", "Name") FROM stdin;
\.


--
-- Name: Account_AccountID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Account_AccountID_seq"', 1, false);


--
-- Name: Author_AuthorID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Author_AuthorID_seq"', 1, false);


--
-- Name: Chair_ChairID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Chair_ChairID_seq"', 1, false);


--
-- Name: ConferenceDetails_DeadlinesID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."ConferenceDetails_DeadlinesID_seq"', 1, false);


--
-- Name: Conference_ConferenceID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Conference_ConferenceID_seq"', 1, false);


--
-- Name: Paper_PaperID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Paper_PaperID_seq"', 1, false);


--
-- Name: Reviewer_ReviewerID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Reviewer_ReviewerID_seq"', 1, false);


--
-- Name: Subtitle_SubtitleID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Subtitle_SubtitleID_seq"', 1, false);


--
-- Name: TopicOfInterest_TopicID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."TopicOfInterest_TopicID_seq"', 1, false);


--
-- Name: AbstractPaper abstractpaper_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AbstractPaper"
    ADD CONSTRAINT abstractpaper_pk PRIMARY KEY ("PaperID");


--
-- Name: Account account_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Account"
    ADD CONSTRAINT account_pk PRIMARY KEY ("AccountID");


--
-- Name: Author author_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Author"
    ADD CONSTRAINT author_pk PRIMARY KEY ("AuthorID");


--
-- Name: Chair chair_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Chair"
    ADD CONSTRAINT chair_pk PRIMARY KEY ("ChairID");


--
-- Name: ChairPaperEvaluation chairpaperevaluation_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ChairPaperEvaluation"
    ADD CONSTRAINT chairpaperevaluation_pk PRIMARY KEY ("PaperID");


--
-- Name: Conference conference_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Conference"
    ADD CONSTRAINT conference_pk PRIMARY KEY ("ConferenceID");


--
-- Name: ConferenceDeadlines conferencedetails_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceDeadlines"
    ADD CONSTRAINT conferencedetails_pk PRIMARY KEY ("DeadlinesID");


--
-- Name: ConferenceSubtitle conferencesubtitle_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceSubtitle"
    ADD CONSTRAINT conferencesubtitle_pk PRIMARY KEY ("SubtitleID");


--
-- Name: ConferenceTopic conferencetopic_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceTopic"
    ADD CONSTRAINT conferencetopic_pk PRIMARY KEY ("TopicID");


--
-- Name: Paper paper_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Paper"
    ADD CONSTRAINT paper_pk PRIMARY KEY ("PaperID");


--
-- Name: PaperAuthor paperauthor_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperAuthor"
    ADD CONSTRAINT paperauthor_pk PRIMARY KEY ("PaperID");


--
-- Name: PaperConference paperconference_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperConference"
    ADD CONSTRAINT paperconference_pk PRIMARY KEY ("ConferenceID");


--
-- Name: Reviewer reviewer_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reviewer"
    ADD CONSTRAINT reviewer_pk PRIMARY KEY ("ReviewerID");


--
-- Name: ReviewerAssignedPaper reviewerassignedpaper_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerAssignedPaper"
    ADD CONSTRAINT reviewerassignedpaper_pk PRIMARY KEY ("PaperID");


--
-- Name: ReviewerPaperBid reviewerpaperbid_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerPaperBid"
    ADD CONSTRAINT reviewerpaperbid_pk PRIMARY KEY ("PaperID");


--
-- Name: ReviewerTopic reviewertopic_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerTopic"
    ADD CONSTRAINT reviewertopic_pk PRIMARY KEY ("TopicID");


--
-- Name: Subtitle subtitle_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Subtitle"
    ADD CONSTRAINT subtitle_pk PRIMARY KEY ("SubtitleID");


--
-- Name: TopicOfInterest topicofinterest_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."TopicOfInterest"
    ADD CONSTRAINT topicofinterest_pk PRIMARY KEY ("TopicID");


--
-- Name: account_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX account_email_uindex ON public."Account" USING btree ("Email");


--
-- Name: account_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX account_username_uindex ON public."Account" USING btree ("Username");


--
-- Name: subtitle_language_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX subtitle_language_uindex ON public."Subtitle" USING btree ("Language");


--
-- Name: topicofinterest_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX topicofinterest_name_uindex ON public."TopicOfInterest" USING btree ("Name");


--
-- Name: AbstractPaper abstractpaper_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AbstractPaper"
    ADD CONSTRAINT abstractpaper_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: Author author_account_accountid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Author"
    ADD CONSTRAINT author_account_accountid_fk FOREIGN KEY ("AuthorID") REFERENCES public."Account"("AccountID");


--
-- Name: Chair chair_account_accountid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Chair"
    ADD CONSTRAINT chair_account_accountid_fk FOREIGN KEY ("ChairID") REFERENCES public."Account"("AccountID");


--
-- Name: ChairPaperEvaluation chairpaperevaluation_chair_chairid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ChairPaperEvaluation"
    ADD CONSTRAINT chairpaperevaluation_chair_chairid_fk FOREIGN KEY ("ChairID") REFERENCES public."Chair"("ChairID");


--
-- Name: ChairPaperEvaluation chairpaperevaluation_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ChairPaperEvaluation"
    ADD CONSTRAINT chairpaperevaluation_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: Conference conference_chair_chairid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Conference"
    ADD CONSTRAINT conference_chair_chairid_fk FOREIGN KEY ("MainOrganiserID") REFERENCES public."Chair"("ChairID");


--
-- Name: Conference conference_conferencedeadlines_deadlinesid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Conference"
    ADD CONSTRAINT conference_conferencedeadlines_deadlinesid_fk FOREIGN KEY ("DeadlinesID") REFERENCES public."ConferenceDeadlines"("DeadlinesID");


--
-- Name: ConferenceSubtitle conferencesubtitle_conference_conferenceid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceSubtitle"
    ADD CONSTRAINT conferencesubtitle_conference_conferenceid_fk FOREIGN KEY ("ConferenceID") REFERENCES public."Conference"("ConferenceID");


--
-- Name: ConferenceSubtitle conferencesubtitle_subtitle_subtitleid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceSubtitle"
    ADD CONSTRAINT conferencesubtitle_subtitle_subtitleid_fk FOREIGN KEY ("SubtitleID") REFERENCES public."Subtitle"("SubtitleID");


--
-- Name: ConferenceTopic conferencetopic_conference_conferenceid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceTopic"
    ADD CONSTRAINT conferencetopic_conference_conferenceid_fk FOREIGN KEY ("ConferenceID") REFERENCES public."Conference"("ConferenceID");


--
-- Name: ConferenceTopic conferencetopic_topicofinterest_topicid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ConferenceTopic"
    ADD CONSTRAINT conferencetopic_topicofinterest_topicid_fk FOREIGN KEY ("TopicID") REFERENCES public."TopicOfInterest"("TopicID");


--
-- Name: Paper paper_topicofinterest_topicid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Paper"
    ADD CONSTRAINT paper_topicofinterest_topicid_fk FOREIGN KEY ("TopicID") REFERENCES public."TopicOfInterest"("TopicID");


--
-- Name: PaperAuthor paperauthor_author_authorid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperAuthor"
    ADD CONSTRAINT paperauthor_author_authorid_fk FOREIGN KEY ("AuthorID") REFERENCES public."Author"("AuthorID");


--
-- Name: PaperAuthor paperauthor_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperAuthor"
    ADD CONSTRAINT paperauthor_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: PaperConference paperconference_chair_chairid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperConference"
    ADD CONSTRAINT paperconference_chair_chairid_fk FOREIGN KEY ("AssigneeID") REFERENCES public."Chair"("ChairID");


--
-- Name: PaperConference paperconference_conference_conferenceid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperConference"
    ADD CONSTRAINT paperconference_conference_conferenceid_fk FOREIGN KEY ("ConferenceID") REFERENCES public."Conference"("ConferenceID");


--
-- Name: PaperConference paperconference_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PaperConference"
    ADD CONSTRAINT paperconference_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: Reviewer reviewer_account_accountid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Reviewer"
    ADD CONSTRAINT reviewer_account_accountid_fk FOREIGN KEY ("ReviewerID") REFERENCES public."Account"("AccountID");


--
-- Name: ReviewerAssignedPaper reviewerassignedpaper_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerAssignedPaper"
    ADD CONSTRAINT reviewerassignedpaper_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: ReviewerAssignedPaper reviewerassignedpaper_reviewer_reviewerid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerAssignedPaper"
    ADD CONSTRAINT reviewerassignedpaper_reviewer_reviewerid_fk FOREIGN KEY ("ReviewerID") REFERENCES public."Reviewer"("ReviewerID");


--
-- Name: ReviewerPaperBid reviewerpaperbid_paper_paperid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerPaperBid"
    ADD CONSTRAINT reviewerpaperbid_paper_paperid_fk FOREIGN KEY ("PaperID") REFERENCES public."Paper"("PaperID");


--
-- Name: ReviewerPaperBid reviewerpaperbid_reviewer_reviewerid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerPaperBid"
    ADD CONSTRAINT reviewerpaperbid_reviewer_reviewerid_fk FOREIGN KEY ("ReviewerID") REFERENCES public."Reviewer"("ReviewerID");


--
-- Name: ReviewerTopic reviewertopic_account_accountid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerTopic"
    ADD CONSTRAINT reviewertopic_account_accountid_fk FOREIGN KEY ("ReviewerID") REFERENCES public."Account"("AccountID");


--
-- Name: ReviewerTopic reviewertopic_topicofinterest_topicid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ReviewerTopic"
    ADD CONSTRAINT reviewertopic_topicofinterest_topicid_fk FOREIGN KEY ("TopicID") REFERENCES public."TopicOfInterest"("TopicID");


--
-- PostgreSQL database dump complete
--

