#If Else syntax Analyzer in Python
def is_valid_if_else(code):
    # Split the code into lines and remove empty lines
    lines = [line for line in code.strip().split("\n") if line.strip()]
    
    # Check if code is empty
    if not lines:
        return "❌ Error: Empty code"
    
    # Check if the code starts with a valid 'if' statement
    if not lines[0].strip().startswith("if ") or not lines[0].strip().endswith(":"):
        return "❌ Error: `if` statement is incorrect (missing `:` or condition)"
    
    # Get the initial indentation level (should be 0 for the if statement)
    if_indent = len(lines[0]) - len(lines[0].lstrip())
    
    # Expected indentation level for the block after 'if'
    expected_block_indent = if_indent + 4  # Python standard is 4 spaces
    
    has_if_body = False
    has_else = False
    else_line_index = None
    
    # Check each line after the 'if' statement
    for i, line in enumerate(lines[1:], start=1):
        stripped = line.strip()
        current_indent = len(line) - len(line.lstrip())
        
        # If this line is an 'else' statement
        if stripped.startswith("else:"):
            # 'else' should have the same indentation as 'if'
            if current_indent != if_indent:
                return "❌ Error: `else` is not aligned properly with `if`"
            has_else = True
            else_line_index = i
            continue
        
        # If we're before the 'else' statement, the line should be part of the 'if' block
        if not has_else or i < else_line_index:
            if current_indent == expected_block_indent:
                has_if_body = True
            elif current_indent < expected_block_indent:
                return "❌ Error: Incorrect indentation inside `if` block"
        # If we're after the 'else' statement, the line should be part of the 'else' block
        elif has_else and i > else_line_index:
            if current_indent != expected_block_indent:
                return "❌ Error: Incorrect indentation inside `else` block"
    
    # Check if the 'if' block has content
    if not has_if_body:
        return "❌ Error: Missing indented code in `if` block"
    
    # If code contains 'else', check if it has a body
    if has_else:
        has_else_body = False
        for line in lines[else_line_index+1:]:
            if len(line) - len(line.lstrip()) == expected_block_indent:
                has_else_body = True
                break
        if not has_else_body:
            return "❌ Error: Missing indented code in `else` block"
        
        return "✅ Syntax is Correct"
    else:
        return "✅ Syntax is Correct (Valid `if` without `else`)"

# Example Test Cases
code1 = """
if x > 10:
    print("x is greater")
else:
    print("x is smaller")
"""
code2 = """
if x > 10
    print("Missing colon")
else
    print("Error")
"""
code3 = """
if x > 10:
print("Missing indentation")
else:
    print("Error")
"""

# Run the syntax analyzer on test cases
print("🔹 Code 1:", is_valid_if_else(code1))  # ✅ Should be correct
print("🔹 Code 2:", is_valid_if_else(code2))  # ❌ Should detect missing `:`
print("🔹 Code 3:", is_valid_if_else(code3))  # ❌ Should detect missing indentation