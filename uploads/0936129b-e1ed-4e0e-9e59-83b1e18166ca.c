#include <stdio.h>


int solution(int age)
{
	return 2023 - age + 1;
}

int solution2(int n)
{
	int result = 0;
	for (int i = 0; i <= n; i++)
	{
		if (i % 2 == 0)
		{
			result += i;
		}
	}

	return result;
}

int main()
{
#pragma region 01.
	/*int n;
	printf("자연수 n을 입력하시오.");
	scanf_s("%d", &n);

	if (n % 2 == 0)
		printf("%d is even", n);
	else
		printf("%d is odd", n);*/
#pragma endregion

#pragma region 02.
	/*int a = 2, b = 3;

	printf("%d + %d = %c", a, b, a + b);*/
#pragma endregion

#pragma region 03.
	//int age;

	//printf("나이를 입력하시오. \n");
	//scanf_s("%d", &age);

	//printf("age :  %d,  result: %d\n", age, solution(age));
#pragma endregion

#pragma region 04.
	//int n = 4;

	//printf("%d\n", solution2(n));
#pragma endregion

#pragma region 05.
	//int n = 5;

	//for (int i = 0; i < 5; i++)
	//{
	//	for (int j = 0; j < i + 1; j++)
	//	{
	//		printf("%d ", j + 1);
	//	}
	//	printf("\n");
	//}
#pragma endregion

#pragma region 06.
/*	int n = 5;

	for (int i = n; i > 0; i--)
	{
		for (int j = 0; j < i; j++)
		{
			printf("%d ", j + 1);
		}
		printf("\n");
	}
	*/
#pragma endregion

#pragma region 07.
	//int n = 5;

	//for (int i = 0; i < n; i++)
	//{
	//	for (int j = 0; j < n; j++)
	//	{
	//		if ((j+1) >= (n-i))
	//			printf("%d ", j + 1);
	//		else
	//			printf("  ");
	//	}
	//	printf("\n");
	//}
#pragma endregion

#pragma region 08.
	/*int n = 5;

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (i <= j)
				printf("%d ", j + 1);
			else
				printf("  ");
		}
		printf("\n");
		}*/
#pragma endregion
}