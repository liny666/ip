# GoGoGoose User Guide

GoGoGoose is a **command-line chatbot** that helps you manage your tasks. It supports three types of tasks — todos, deadlines, and events — and saves your list automatically between sessions.

---

## Quick Start

1. Ensure you have Java 17 or later installed.
2. Download the latest `ip.jar` from the releases page.
3. Run it with: `java -jar ip.jar`
4. Type a command and press Enter.
5. Type `bye` to exit.

---

## Features

### Add a Todo — `todo`

Adds a task with no date or time attached.

**Format:** `todo DESCRIPTION`

**Example:**
```
todo Buy groceries
```
**Output:**
```
Got it. I've added this task:
  [T][ ] Buy groceries
Now you have 1 tasks in the list.
```

---

### Add a Deadline — `deadline`

Adds a task that must be done by a specific date or time.

**Format:** `deadline DESCRIPTION /by DATE`

**Example:**
```
deadline Submit assignment /by 2026-03-10
```
**Output:**
```
Got it. I've added this task:
  [D][ ] Submit assignment (by: 2026-03-10)
Now you have 2 tasks in the list.
```

---

### Add an Event — `event`

Adds a task that spans a time range.

**Format:** `event DESCRIPTION /from START /to END`

**Example:**
```
event Team meeting /from Mon 2pm /to Mon 4pm
```
**Output:**
```
Got it. I've added this task:
  [E][ ] Team meeting (from: Mon 2pm to: Mon 4pm)
Now you have 3 tasks in the list.
```

---

### List All Tasks — `list`

Displays all tasks currently in your list.

**Format:** `list`

**Output:**
```
Here are the tasks in your list:
1.[T][ ] Buy groceries
2.[D][ ] Submit assignment (by: 2026-03-10)
3.[E][ ] Team meeting (from: Mon 2pm to: Mon 4pm)
```

---

### Mark a Task as Done — `mark`

Marks a task as completed.

**Format:** `mark INDEX`

**Example:**
```
mark 2
```
**Output:**
```
Nice! I've marked this task as done:
  [D][X] Submit assignment (by: 2026-03-10)
```

---

### Mark a Task as Not Done — `unmark`

Marks a previously completed task as not done.

**Format:** `unmark INDEX`

**Example:**
```
unmark 2
```
**Output:**
```
OK, I've marked this task as not done yet:
  [D][ ] Submit assignment (by: 2026-03-10)
```

---

### Delete a Task — `delete`

Removes a task from the list permanently.

**Format:** `delete INDEX`

**Example:**
```
delete 1
```
**Output:**
```
Noted. I've removed this task:
  [T][ ] Buy groceries
Now you have 2 tasks in the list.
```

---

### Find Tasks by Keyword — `find`

Searches for tasks whose descriptions contain the given keyword.

**Format:** `find KEYWORD`

**Example:**
```
find assignment
```
**Output:**
```
Here are the matching tasks in your list:
1.[D][ ] Submit assignment (by: 2026-03-10)
```

---

### Exit — `bye`

Exits the application. Your tasks are saved automatically.

**Format:** `bye`

---

## Task Types at a Glance

| Symbol | Type     | Command    |
|--------|----------|------------|
| `[T]`  | Todo     | `todo`     |
| `[D]`  | Deadline | `deadline` |
| `[E]`  | Event    | `event`    |

The second bracket shows completion status: `[X]` = done, `[ ]` = not done.

---

## Data Storage

Tasks are saved automatically to `data/goose.txt` after every change. The file is created on first run. Do not manually edit the file unless you know the storage format.
